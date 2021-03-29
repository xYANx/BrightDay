package com.navoichykyan.brightday.data.api

import com.navoichykyan.brightday.data.models.WeatherDataModel
import io.reactivex.Single

interface WeatherApi {
    fun getWeatherList(url: String): Single<List<List<WeatherDataModel>>>
}