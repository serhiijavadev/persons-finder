package com.persons.finder.data.repositories

import com.persons.finder.data.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonJpaRepository : JpaRepository<PersonEntity, Long>