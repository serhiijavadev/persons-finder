package com.persons.finder.api

import com.persons.finder.api.dtos.*
import com.persons.finder.domain.services.PersonsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Positive

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

    @GetMapping
    fun getPersons(
        @RequestParam(required = true) id: List<Long>): ResponseEntity<List<PersonDto>> {
        val persons = if (id.isEmpty()) {
            emptyList()
        } else {
            personsService.getByIds(id).map { PersonDto.fromEntity(it) }
        }
        return ResponseEntity(persons, HttpStatus.OK)
    }

    @GetMapping("/{id}")
    fun getPerson(@PathVariable id: Long): ResponseEntity<PersonDto> {
        val person = personsService.getById(id)
        return ResponseEntity(PersonDto.fromEntity(person), HttpStatus.OK)
    }

    @PutMapping("/{id}/location")
    fun updateLocation(
        @PathVariable id: Long,
        @Valid @RequestBody request: PersonLocationRequest
    ): ResponseEntity<LocationDto> {
        val location = personsService.updateLocation(id, request.latitude, request.longitude)
        return ResponseEntity(LocationDto.fromEntity(location), HttpStatus.OK)
    }

    @GetMapping("/nearby")
    fun findNearby(
        @RequestParam lat: Double,
        @RequestParam lon: Double,
        @RequestParam @Positive radiusKm: Double
    ): ResponseEntity<List<PersonNearbyDto>> {
        val nearbyPersons = personsService.findNearby(lat, lon, radiusKm)
        return ResponseEntity(nearbyPersons, HttpStatus.OK)
    }
}
