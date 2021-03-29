package com.navoichykyan.brightday.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationViewModel : ViewModel(){
    private val locationMap = MutableLiveData<Map<String, Double>>()
    private val url = MutableLiveData<String>()

    val observableLocationMap : LiveData<Map<String, Double>> = locationMap
    val observableUrl : LiveData<String> = url

    fun selectUrl(inputUrl: String) {
        url.value = inputUrl
    }

    fun getSelectedUrl(): String? {
        return url.value
    }

    fun select(inputLocationMap: Map<String, Double>) {
        locationMap.value = inputLocationMap
    }

    fun getSelected(): LiveData<Map<String, Double>>? {
        return locationMap
    }

    companion object {
        fun newInstance() = LocationViewModel()
    }
}