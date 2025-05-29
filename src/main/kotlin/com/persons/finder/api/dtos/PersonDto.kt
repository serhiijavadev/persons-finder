package com.persons.finder.api.dtos

import com.persons.finder.domain.validation.ValidLatitude
import com.persons.finder.domain.validation.ValidLongitude
import com.persons.finder.persistence.LocationEntity
import com.persons.finder.persistence.PersonEntity
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class PersonDto(
    val id: Long,
    val name: String
) {
    companion object {
        fun fromEntity(person: PersonEntity): PersonDto {
            return PersonDto(
                id = person.id,
                name = person.name
            )
        }
    }
}

data class CreatePersonRequest(
    @field:NotBlank(message = "Name is required")
    val name: String
) {
    fun toEntity(): PersonEntity {
        return PersonEntity(
            name = name
        )
    }
}

data class LocationDto(
    val id: Long,
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        fun fromEntity(location: LocationEntity): LocationDto {
            return LocationDto(
                id = location.id,
                latitude = location.latitude,
                longitude = location.longitude
            )
        }
    }
}

data class PersonLocationRequest(
    @field:ValidLatitude
    @field:NotNull(message = "Latitude is required")
    val latitude: Double,

    @field:ValidLongitude
    @field:NotNull(message = "Longitude is required")
    val longitude: Double
)

data class PersonNearbyDto(
    val id: Long,
    val distance: Double? = null
)

