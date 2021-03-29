package com.navoichykyan.brightday.ui.viewmodel

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.navoichykyan.brightday.data.api.WeatherApi
import com.navoichykyan.brightday.data.db.CityDao
import com.navoichykyan.brightday.data.db.CityDatabase
import com.navoichykyan.brightday.data.db.CityEntity
import com.navoichykyan.brightday.domain.usecase.LoadWeatherDataUseCase
import com.navoichykyan.brightday.data.models.WeatherDataModel
import io.reactivex.disposables.Disposable

class WeatherViewModel(private val loaDaWeatherDataUseCase: LoadWeatherDataUseCase) : ViewModel() {
    private var disposable: Disposable? = null
    private val _weatherModel = MutableLiveData<List<List<WeatherDataModel>>>()
    private val _progress = MutableLiveData<Int>()
    private val _visibility = MutableLiveData<Int>()
    private val _toastText = MutableLiveData<String>()

    val weatherModel : LiveData<List<List<WeatherDataModel>>> = _weatherModel
    val progress : LiveData<Int> = _progress
    val visibility : LiveData<Int> = _visibility

    fun set(list: List<List<WeatherDataModel>>){
        _weatherModel.value = list
    }

    fun setVisibility(view: Int){
        _visibility.value = view
    }

    private fun setProgress(view: Int){
        _progress.value = view
    }

    private fun setToastText(text: String){
        _toastText.value = text
    }

    fun load(url: String){
        setProgress(View.VISIBLE)
        disposable = loaDaWeatherDataUseCase.loadWeather(url)
            .subscribe({ list ->
                set(list)
                setVisibility(View.VISIBLE)
                setProgress(View.INVISIBLE)
            }, { throwable ->
                setToastText(throwable.message!!)
                setProgress(View.INVISIBLE)
            })
    }

    private fun dispose() {
        disposable?.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
    companion object{
        @JvmStatic @BindingAdapter("app:url")
        fun loadImage(view: ImageView, icon: String){
            if(icon != "null") {
                Glide.with(view.context)
                    .load(icon)
                    .into(view)
            }
        }
    }
}