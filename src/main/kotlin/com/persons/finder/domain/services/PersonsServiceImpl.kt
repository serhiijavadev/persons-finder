package com.persons.finder.domain.services

import com.persons.finder.data.PersonEntity
import com.persons.finder.data.repositories.PersonJpaRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
class PersonsServiceImpl(
    private val personRepository: PersonJpaRepository,
) : PersonsService {

    private val logger = LoggerFactory.getLogger(PersonsServiceImpl::class.java)

    @Transactional(readOnly = true)
    override fun getById(id: Long): PersonEntity {
        return personRepository.findById(id)
            .orElseThrow { 
                val errorMessage = "Person not found with id: $id"
                logger.error(errorMessage)
                EntityNotFoundException(errorMessage)
            }
    }

    @Transactional(readOnly = true)
    override fun getByIds(ids: List<Long>): List<PersonEntity> {
        return personRepository.findAllById(ids)
    }

    @Transactional
    override fun save(person: PersonEntity): PersonEntity {
        return personRepository.save(person)
    }

}
