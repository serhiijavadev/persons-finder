package com.persons.finder.data.repositories

import com.persons.finder.data.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationJpaRepository : JpaRepository<LocationEntity, Long>