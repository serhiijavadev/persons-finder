package com.persons.finder.domain.services

import com.persons.finder.data.LocationEntity

interface LocationsService {
    fun addLocation(location: LocationEntity): LocationEntity
    fun removeLocation(id: Long)
    fun findAround(latitude: Double, longitude: Double, radiusInKm: Double) : List<LocationEntity>
}
