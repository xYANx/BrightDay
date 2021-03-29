package com.navoichykyan.brightday.data.repository

import android.annotation.SuppressLint
import android.app.Application
import com.google.android.gms.location.LocationServices
import com.navoichykyan.brightday.utility.Provider
import com.navoichykyan.brightday.utility.extensions.checkLocationPermission
import com.navoichykyan.brightday.domain.LocationRepository
import com.navoichykyan.brightday.utility.extensions.isGPSEnabled
import com.navoichykyan.brightday.ui.viewmodel.LocationViewModel
import com.navoichykyan.brightday.utility.extensions.setUrl

class LocationRepositoryImpl(private val application: Application): LocationRepository {
    private val locationModel = Provider.newInstance().getProvider().get(LocationViewModel.newInstance()::class.java)

    @SuppressLint("MissingPermission")
    override fun getLocation(){
        if (application.isGPSEnabled() && application.checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(application)
                ?.lastLocation
                ?.addOnSuccessListener { location: android.location.Location? ->
                    if (location != null) {
                        val locationMap = mapOf(
                            "lat" to location.latitude,
                            "lon" to location.longitude)
                        locationModel.select(locationMap)
                        locationModel.selectUrl(
                            setUrl(
                                location.latitude.toString(),
                                location.longitude.toString()
                            )
                        )
                    }
                }
        }
    }
}