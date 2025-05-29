package com.persons.finder.persistence

import javax.persistence.*

@Entity
@Table(name = "locations")
data class LocationEntity(
    @Id
    val id: Long = 0,

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    val person: PersonEntity? = null,

    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
