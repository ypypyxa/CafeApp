package com.pivnoydevelopment.cafeapp.features.locations.util

import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition

object MapCameraUtils {
    fun calculateCameraPosition(points: List<Point>): CameraPosition {
        if (points.isEmpty()) {
            return CameraPosition(Point(55.751244, 37.618423), 12f, 0f, 0f)
        }

        val minLat = points.minOf { it.latitude }
        val maxLat = points.maxOf { it.latitude }
        val minLon = points.minOf { it.longitude }
        val maxLon = points.maxOf { it.longitude }

        val midLat = (minLat + maxLat) / 2
        val midLon = (minLon + maxLon) / 2

        val latDiff = maxLat - minLat
        val lonDiff = maxLon - minLon
        val maxDiff = maxOf(latDiff, lonDiff)

        val paddingFactor = 1.2
        val adjustedDiff = maxDiff * paddingFactor

        val zoom = when {
            adjustedDiff < 0.005 -> 15f
            adjustedDiff < 0.05 -> 12f
            adjustedDiff < 0.5 -> 10f
            adjustedDiff < 1.0 -> 8f
            else -> 5f
        }

        return CameraPosition(Point(midLat, midLon), zoom, 0f, 0f)
    }
}