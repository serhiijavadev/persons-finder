package com.persons.finder.persistence.repositories

import com.persons.finder.persistence.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonJpaRepository : JpaRepository<PersonEntity, Long>