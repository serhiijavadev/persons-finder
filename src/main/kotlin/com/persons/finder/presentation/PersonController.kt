package com.persons.finder.presentation

import com.persons.finder.domain.services.PersonsService
import com.persons.finder.presentation.dtos.CreatePersonRequest
import com.persons.finder.presentation.dtos.PersonDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/persons")
class PersonController(
    private val personsService: PersonsService
) {

    @PostMapping
    fun createPerson(@Valid @RequestBody request: CreatePersonRequest): ResponseEntity<PersonDto> {
        val person = personsService.save(request.toEntity())
        return ResponseEntity(PersonDto.fromEntity(person), HttpStatus.CREATED)
    }


    /*
        TODO PUT API to update/create someone's location using latitude and longitude
        (JSON) Body
     */


    /*
        TODO GET API to retrieve people around query location with a radius in KM, Use query param for radius.
        TODO API just return a list of persons ids (JSON)
        // Example
        // John wants to know who is around his location within a radius of 10km
        // API would be called using John's id and a radius 10km
     */

    /*
        TODO GET API to retrieve a person or persons name using their ids
        // Example
        // John has the list of people around them, now they need to retrieve everybody's names to display in the app
        // API would be called using person or persons ids
     */

    @GetMapping("")
    fun getExample(): String {
        return "Hello Example"
    }

}