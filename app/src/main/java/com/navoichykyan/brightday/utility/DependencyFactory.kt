package com.navoichykyan.brightday.utility

import android.app.Application
import android.content.Context
import androidx.work.WorkManager
import com.navoichykyan.brightday.data.api.WeatherApiImpl
import com.navoichykyan.brightday.data.repository.AppRepositoryImpl
import com.navoichykyan.brightday.data.repository.LocationRepositoryImpl
import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.domain.usecase.*

class DependencyFactory(private val context: Context) {

    fun loadWeatherDataUseCase() : LoadWeatherDataUseCase {
        return LoadWeatherDataUseCase(
            getAppRepository(),
            getWeatherApi()
        )
    }

    fun loadCitiesDataUseCase() : LoadCitiesDataUseCase {
        return LoadCitiesDataUseCase(
            getAppRepository()
        )
    }

    fun insertCityUseCase() : InsertCityUseCase {
        return InsertCityUseCase(
            getAppRepository()
        )
    }

    fun deleteCityUseCase() : DeleteCityUseCase {
        return DeleteCityUseCase(
            getAppRepository()
        )
    }

    fun getWorker(): WorkManager{
        return WorkManager.getInstance(context)
    }

    fun getDataBase() : CityDatabase{
        return CityDatabase.getDatabase(context)!!
    }

    fun loadLocation() : LocationUseCase{
        return LocationUseCase(getLocationRepository())
    }

    private fun getLocationRepository(): LocationRepositoryImpl {
        return LocationRepositoryImpl(
            context as Application
        )
    }

    private fun getWeatherApi(): WeatherApiImpl {
        val w = WorkManager.getInstance(context)
        return WeatherApiImpl()
    }

    private fun getAppRepository(): AppRepositoryImpl {
        return AppRepositoryImpl()
    }

}