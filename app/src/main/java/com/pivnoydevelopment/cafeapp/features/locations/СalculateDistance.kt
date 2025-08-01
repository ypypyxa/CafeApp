package com.pivnoydevelopment.cafeapp.features.locations

fun calculateDistance(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): Float? {
    if (lat1 == 0.0 && lon1 == 0.0) return null

    val result = FloatArray(1)
    android.location.Location.distanceBetween(
        lat1,
        lon1,
        lat2,
        lon2,
        result)
    return (result[0] / 1000)
}