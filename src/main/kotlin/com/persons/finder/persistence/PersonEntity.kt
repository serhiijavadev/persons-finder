package com.persons.finder.persistence

import javax.persistence.*

@Entity
@Table(name = "persons")
data class PersonEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val name: String = "",

    @OneToOne(mappedBy = "person", cascade = [CascadeType.ALL])
    var location: LocationEntity? = null
)
