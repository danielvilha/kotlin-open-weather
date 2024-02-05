package com.danielvilha.models

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat

/**
 * Created by Daniel Ferreira de Lima Vilha 30/01/2024.
 */
@Deprecated("Deprecated in Java")
class LocationHelper(private val activity: Activity) {

    private val locationManager: LocationManager by lazy {
        activity.getSystemService(Activity.LOCATION_SERVICE) as LocationManager
    }

    fun requestLocationUpdates(callback: (location: Location?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_LOCATION
            )
            return
        }

        val locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                callback(location)
                locationManager.removeUpdates(this)
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        locationManager.requestSingleUpdate(
            LocationManager.GPS_PROVIDER,
            locationListener,
            null
        )
    }

    companion object {
        private const val PERMISSIONS_REQUEST_LOCATION = 100
    }
}
