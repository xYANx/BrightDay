package com.navoichykyan.brightday.domain.usecase

import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.data.db.CityEntity
import com.navoichykyan.brightday.domain.AppRepository
import io.reactivex.Single

class LoadCitiesDataUseCase(private val appRepository: AppRepository) {
    fun loadCities(db: CityDatabase): Single<List<CityEntity>> {
        return appRepository.loadCities(db)
    }
}