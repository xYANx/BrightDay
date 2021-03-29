package com.navoichykyan.brightday.domain.usecase

import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.domain.AppRepository
import io.reactivex.Single

class InsertCityUseCase(private val appRepository: AppRepository) {
    fun insertCity(db: CityDatabase, city: String): Single<String> {
        return appRepository.insertCity(db, city)
    }
}