package com.persons.finder.domain.services

import com.persons.finder.api.dtos.PersonNearbyDto
import com.persons.finder.domain.exceptions.PersonNotFoundException
import com.persons.finder.domain.utils.DistanceUtils
import com.persons.finder.persistence.LocationEntity
import com.persons.finder.persistence.PersonEntity
import com.persons.finder.persistence.repositories.LocationJpaRepository
import com.persons.finder.persistence.repositories.PersonJpaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonsServiceImpl(
    private val personRepository: PersonJpaRepository,
    private val locationsService: LocationsService,
    private val locationRepository: LocationJpaRepository
) : PersonsService {

    private val logger = LoggerFactory.getLogger(PersonsServiceImpl::class.java)

    @Transactional(readOnly = true)
    override fun getById(id: Long): PersonEntity {
        return personRepository.findById(id)
            .orElseThrow {
                val errorMessage = "Person not found with id: $id"
                logger.error(errorMessage)
                PersonNotFoundException(errorMessage)
            }
    }

    @Transactional(readOnly = true)
    override fun getByIds(ids: List<Long>): List<PersonEntity> {
        return personRepository.findAllById(ids)
    }

    @Transactional
    override fun save(person: PersonEntity): PersonEntity {
        return personRepository.save(person)
    }

    @Transactional
    override fun updateLocation(personId: Long, latitude: Double, longitude: Double): LocationEntity {
        val person = personRepository.findById(personId)
            .orElseThrow {
                val errorMessage = "Person not found with id: $personId"
                logger.error(errorMessage)
                PersonNotFoundException(errorMessage)
            }

        val location = locationRepository.findById(personId).orElse(null)
        if (location != null) {
            val updated = location.copy(latitude = latitude, longitude = longitude)
            return locationsService.addLocation(updated)
        } else {
            return locationsService.addLocation(
                LocationEntity(
                    person = person,
                    latitude = latitude,
                    longitude = longitude
                )
            )
        }
    }

    override fun findNearby(latitude: Double, longitude: Double, radiusInKm: Double): List<PersonNearbyDto> {
        val locations = locationsService.findAround(latitude, longitude, radiusInKm)

        return locations.map { location ->
            val distance = DistanceUtils.calculateDistance(latitude, longitude, location.latitude, location.longitude)
            PersonNearbyDto(location.id, distance)
        }.sortedBy { it.distance }
    }
}
