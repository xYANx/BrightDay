package com.navoichykyan.brightday.data.repository

import com.navoichykyan.brightday.data.api.WeatherApi
import com.navoichykyan.brightday.data.api.WeatherApiImpl
import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.data.db.CityEntity
import com.navoichykyan.brightday.domain.AppRepository
import com.navoichykyan.brightday.data.models.WeatherDataModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppRepositoryImpl : AppRepository {
    override fun loadWeather(url: String, weatherApi: WeatherApi): Single<List<List<WeatherDataModel>>> {
        return weatherApi.getWeatherList(url)
    }

    override fun loadCities(db: CityDatabase): Single<List<CityEntity>> {
        val dbDao = db.cityDao()
        return Single.create<List<CityEntity>> {
            it.onSuccess(dbDao.getAll())
        }.subscribeOn(Schedulers.io())
          //  .map { jsonData -> newsDataModelMapper(jsonData) }
            .observeOn(AndroidSchedulers.mainThread())
      //  return dbDao.getAll()
    }

    override fun deleteCity(db: CityDatabase, city: CityEntity): Single<String> {
        val dbDao = db.cityDao()

        return Single.create<String> {
            dbDao.delete(city)
            it.onSuccess("Good")
        }.subscribeOn(Schedulers.io())
            //  .map { jsonData -> newsDataModelMapper(jsonData) }
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertCity(db: CityDatabase, city: String): Single<String> {
        val dbDao = db.cityDao()

        return Single.create<String> {
            dbDao.insert(CityEntity(city = city))
            it.onSuccess("Good")
        }.subscribeOn(Schedulers.io())
            //  .map { jsonData -> newsDataModelMapper(jsonData) }
            .observeOn(AndroidSchedulers.mainThread())
    }
}