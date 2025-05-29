package com.persons.finder.persistence.repositories

import com.persons.finder.persistence.LocationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface LocationJpaRepository : JpaRepository<LocationEntity, Long> {
    @Query(
        """
    SELECT * FROM locations l
    WHERE
        l.latitude BETWEEN :latMin AND :latMax
        AND l.longitude BETWEEN :lonMin AND :lonMax
        AND 6371 * acos(
            least(1.0, 
                cos(radians(:lat)) * cos(radians(l.latitude)) *
                cos(radians(l.longitude) - radians(:lon)) +
                sin(radians(:lat)) * sin(radians(l.latitude))
            )
        ) <= :radiusKm
    """,
        nativeQuery = true
    )
    fun findLocationsWithinRadius(
        lat: Double, lon: Double, radiusKm: Double,
        latMin: Double, latMax: Double, lonMin: Double, lonMax: Double
    ): List<LocationEntity>
}