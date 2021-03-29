package com.navoichykyan.brightday.domain

import com.navoichykyan.brightday.data.api.WeatherApi
import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.data.db.CityEntity
import com.navoichykyan.brightday.data.models.WeatherDataModel
import io.reactivex.Single

interface AppRepository {
    fun loadWeather(url: String, weatherApi: WeatherApi): Single<List<List<WeatherDataModel>>>
    fun loadCities(db: CityDatabase): Single<List<CityEntity>>
    fun deleteCity(db: CityDatabase, city: CityEntity): Single<String>
    fun insertCity(db: CityDatabase, city: String): Single<String>
}