package com.navoichykyan.brightday.data.api

import com.navoichykyan.brightday.data.models.WeatherDataModel
import com.navoichykyan.brightday.utility.WeatherDataModelMapper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import java.io.IOException

class WeatherApiImpl: WeatherApi {
    override fun getWeatherList(url: String): Single<List<List<WeatherDataModel>>> {
        val newsDataModelMapper: (String) -> List<List<WeatherDataModel>> =
            WeatherDataModelMapper()
        val request = Request.Builder()
            .url(url)
            .build()
        val okHttp = OkHttpClient()

        return Single.create<String> { emitter ->
            okHttp.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    emitter.onError(Throwable("Check your internet connection!"))
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        emitter.onError(Throwable("Server error code: ${response.code}"))
                    } else if (response.body == null) emitter.onError(Throwable("Body is null"))
                    else emitter.onSuccess((response.body as ResponseBody).string())
                }
            })
        }.subscribeOn(Schedulers.io())
            .map { jsonData -> newsDataModelMapper(jsonData) }
            .observeOn(AndroidSchedulers.mainThread())

    }
}
