package com.persons.finder.presentation.dtos

import com.persons.finder.data.PersonEntity
import javax.validation.constraints.NotBlank

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

