package com.persons.finder.domain.services

import com.persons.finder.domain.utils.calculateBoundingBox
import com.persons.finder.persistence.LocationEntity
import com.persons.finder.persistence.repositories.LocationJpaRepository
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

    @Transactional(readOnly = true)
    override fun findAround(latitude: Double, longitude: Double, radiusInKm: Double): List<LocationEntity> {
        val box = calculateBoundingBox(latitude, longitude, radiusInKm)
        return locationRepository.findLocationsWithinRadius(
            lat = latitude,
            lon = longitude,
            radiusKm = radiusInKm,
            latMin = box.latMin,
            latMax = box.latMax,
            lonMin = box.lonMin,
            lonMax = box.lonMax
        )
    }
}
