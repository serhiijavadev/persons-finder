package com.persons.finder.domain.services

import com.persons.finder.data.PersonEntity

interface PersonsService {
    fun getById(id: Long): PersonEntity
    fun getByIds(ids: List<Long>): List<PersonEntity>
    fun save(person: PersonEntity): PersonEntity
}