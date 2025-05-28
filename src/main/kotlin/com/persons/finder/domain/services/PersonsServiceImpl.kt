package com.persons.finder.domain.services

import com.persons.finder.data.LocationEntity
import com.persons.finder.data.PersonEntity
import com.persons.finder.data.repositories.LocationJpaRepository
import com.persons.finder.data.repositories.PersonJpaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

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
                EntityNotFoundException(errorMessage)
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
                EntityNotFoundException(errorMessage)
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

}
