package com.navoichykyan.brightday.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.navoichykyan.brightday.data.models.Response
import com.navoichykyan.brightday.utility.workmanager.WeatherWorker
import java.util.concurrent.TimeUnit

class MainActivityViewModel(private val application: Application, private val workManager: WorkManager): ViewModel() {
    val enableLocation: MutableLiveData<Response<Boolean>> = MutableLiveData()

    fun locationSetup() {
        val locationRequest = LocationRequest()
            .setFastestInterval(1500)
            .setInterval(3000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        enableLocation.value = Response.loading()
        LocationServices.getSettingsClient(application)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest)
                    .setAlwaysShow(true)
                    .build())
            .addOnSuccessListener {
                enableLocation.value = Response.success(true) }
            .addOnFailureListener {
                enableLocation.value = Response.error(it)
            }
    }

    fun startWork(){
        workManager.enqueue(
            PeriodicWorkRequest.Builder(
                WeatherWorker::class.java,
                15,
                TimeUnit.MINUTES
            ).build()
        )
    }

    override fun onCleared() {
        super.onCleared()
        workManager.cancelAllWork()
    }
}