package com.persons.finder.domain.services

import com.persons.finder.data.PersonEntity
import com.persons.finder.data.repositories.PersonJpaRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PersonsServiceImpl(
    private val personRepository: PersonJpaRepository,
) : PersonsService {

    override fun getById(id: Long): PersonEntity {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun save(person: PersonEntity): PersonEntity {
        return personRepository.save(person)
    }

}