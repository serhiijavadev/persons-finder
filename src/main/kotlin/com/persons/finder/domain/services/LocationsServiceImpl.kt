package com.persons.finder.domain.services

import com.persons.finder.data.LocationEntity
import com.persons.finder.data.repositories.LocationJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class LocationsServiceImpl(
    private val locationRepository: LocationJpaRepository
) : LocationsService {

    @Transactional
    override fun addLocation(location: LocationEntity): LocationEntity {
        return locationRepository.save(location)
    }

    @Transactional
    override fun removeLocation(id: Long) {
        locationRepository.deleteById(id)
    }

    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): List<LocationEntity> {
        TODO("Not yet implemented")
    }

}
