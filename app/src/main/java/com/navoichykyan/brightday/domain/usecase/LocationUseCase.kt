package com.navoichykyan.brightday.domain.usecase

import android.content.Context
import com.navoichykyan.brightday.domain.LocationRepository


class LocationUseCase(private val locationRepository: LocationRepository) {
    fun loadLocation(){
        return  locationRepository.getLocation()
    }
}