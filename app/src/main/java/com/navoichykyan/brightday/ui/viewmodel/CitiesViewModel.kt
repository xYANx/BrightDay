package com.navoichykyan.brightday.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.data.db.CityEntity
import com.navoichykyan.brightday.domain.usecase.DeleteCityUseCase
import com.navoichykyan.brightday.domain.usecase.InsertCityUseCase
import com.navoichykyan.brightday.domain.usecase.LoadCitiesDataUseCase
import io.reactivex.disposables.Disposable
private const val TAG = "CitiesViewModel"
class CitiesViewModel(private val loadCitiesDataUseCase: LoadCitiesDataUseCase, private val insertCityUseCase: InsertCityUseCase, private val deleteCityUseCase: DeleteCityUseCase, private val db: CityDatabase) : ViewModel() {
    private var disposable: Disposable? = null
    private val _citiesList = MutableLiveData<MutableList<CityEntity>>()

    val citiesList : LiveData<MutableList<CityEntity>> = _citiesList

    fun getCities(){
        disposable = loadCitiesDataUseCase.loadCities(db)
            .subscribe({ list ->
                _citiesList.value = list as MutableList<CityEntity>?
            }, { throwable ->
                Log.d(TAG, "getCities method - " + throwable.message.toString())
            })
    }

    fun insertCity(city: String){
        disposable = insertCityUseCase.insertCity(db, city)
            .subscribe({ list ->
                val newList = _citiesList.value
                newList?.add(CityEntity(city = city))
                _citiesList.value = newList
            }, { throwable ->
                Log.d(TAG, "insertCity method - " + throwable.message.toString())
            })
    }

    fun deleteCity(city: CityEntity){
        disposable = deleteCityUseCase.deleteCity(db, city)
            .subscribe({ list ->
                val newList = _citiesList.value
                newList?.remove(city)
                _citiesList.value = newList
            }, { throwable ->
                Log.d(TAG, "deleteCity method - " + throwable.message.toString())
            })
    }
}