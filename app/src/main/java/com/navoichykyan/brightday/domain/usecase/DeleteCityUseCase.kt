package com.navoichykyan.brightday.domain.usecase

import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.data.db.CityEntity
import com.navoichykyan.brightday.domain.AppRepository
import io.reactivex.Single

class DeleteCityUseCase(private val appRepository: AppRepository) {
    fun deleteCity(db: CityDatabase, city: CityEntity): Single<String> {
        return appRepository.deleteCity(db, city)
    }
}