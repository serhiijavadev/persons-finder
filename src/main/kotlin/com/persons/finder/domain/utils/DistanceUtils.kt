package com.persons.finder.domain.utils

import kotlin.math.*

object DistanceUtils {

    private const val EARTH_RADIUS_KM = 6371.0

    /**
     * Calculates the distance in kilometers between two latitude/longitude points using the Haversine formula.
     */
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val lat1Rad = toRadians(lat1)
        val lat2Rad = toRadians(lat2)
        val deltaLat = lat2Rad - lat1Rad
        val deltaLon = toRadians(lon2 - lon1)

        val a = sin(deltaLat / 2).pow(2) +
                cos(lat1Rad) * cos(lat2Rad) * sin(deltaLon / 2).pow(2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS_KM * c
    }

    private fun toRadians(degrees: Double) = Math.toRadians(degrees)
}