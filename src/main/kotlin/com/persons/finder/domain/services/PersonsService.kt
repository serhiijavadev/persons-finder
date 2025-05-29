package com.persons.finder.domain.services

import com.persons.finder.api.dtos.PersonNearbyDto
import com.persons.finder.persistence.LocationEntity
import com.persons.finder.persistence.PersonEntity

interface PersonsService {
    fun getById(id: Long): PersonEntity
    fun getByIds(ids: List<Long>): List<PersonEntity>
    fun save(person: PersonEntity): PersonEntity
    fun updateLocation(personId: Long, latitude: Double, longitude: Double): LocationEntity
    fun findNearby(latitude: Double, longitude: Double, radiusInKm: Double): List<PersonNearbyDto>
}