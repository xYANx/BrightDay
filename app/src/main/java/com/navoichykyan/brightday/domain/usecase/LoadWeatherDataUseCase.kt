package com.navoichykyan.brightday.domain.usecase

import com.navoichykyan.brightday.data.api.WeatherApi
import com.navoichykyan.brightday.domain.AppRepository
import com.navoichykyan.brightday.data.models.WeatherDataModel
import io.reactivex.Single

class LoadWeatherDataUseCase(private val appRepository: AppRepository, private val weatherApi: WeatherApi) {
    fun loadWeather(url: String): Single<List<List<WeatherDataModel>>> {
        return appRepository.loadWeather(url, weatherApi)
    }
}