package com.persons.finder.domain.utils

import kotlin.math.cos

data class BoundingBox(
    val latMin: Double,
    val latMax: Double,
    val lonMin: Double,
    val lonMax: Double
)

fun calculateBoundingBox(lat: Double, lon: Double, radiusKm: Double): BoundingBox {
    val earthRadius = 6371.0
    val deltaLat = Math.toDegrees(radiusKm / earthRadius)
    val deltaLon = Math.toDegrees(radiusKm / earthRadius / cos(Math.toRadians(lat)))

    return BoundingBox(
        latMin = lat - deltaLat,
        latMax = lat + deltaLat,
        lonMin = lon - deltaLon,
        lonMax = lon + deltaLon
    )
}