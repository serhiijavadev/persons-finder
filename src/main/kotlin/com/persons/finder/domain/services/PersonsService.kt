package com.persons.finder.domain.services

import com.persons.finder.data.LocationEntity
import com.persons.finder.data.PersonEntity
import com.persons.finder.presentation.dtos.PersonNearbyDto

interface PersonsService {
    fun getById(id: Long): PersonEntity
    fun getByIds(ids: List<Long>): List<PersonEntity>
    fun save(person: PersonEntity): PersonEntity
    fun updateLocation(personId: Long, latitude: Double, longitude: Double): LocationEntity
    fun findNearby(latitude: Double, longitude: Double, radiusInKm: Double): List<PersonNearbyDto>
}