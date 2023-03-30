package net.kosev.abstractingapi

import android.annotation.SuppressLint
import android.location.Location
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicePositionRepository @Inject constructor(
    private val locationService: LocationServicesWrapper
) {

    @SuppressLint("MissingPermission")
    suspend fun loadCurrentPosition(): Position {
        val location = locationService.getLastLocation()
        return Position(location.latitude, location.longitude)
    }


    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Float {
        val location1 = Location("").apply {
            latitude = lat1
            longitude = lon1
        }
        val location2 = Location("").apply {
            latitude = lat2
            longitude = lon2
        }

        return location1.distanceTo(location2)
    }
}

data class Position(
    val latitude: Double,
    val longitude: Double
)