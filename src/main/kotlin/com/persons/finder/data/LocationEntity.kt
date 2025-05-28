package com.persons.finder.data

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.MapsId
import javax.persistence.OneToOne
import javax.persistence.Table

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
