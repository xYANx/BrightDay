package com.navoichykyan.brightday.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.navoichykyan.brightday.data.models.WeatherDataModel


class SharedViewModel: ViewModel() {
    private val forecastList = MutableLiveData<List<List<WeatherDataModel>>>()

    fun select(inputForecastList: List<List<WeatherDataModel>>) {
        forecastList.value = inputForecastList
    }

    fun getSelected(): LiveData<List<List<WeatherDataModel>>>? {
        return forecastList
    }
}