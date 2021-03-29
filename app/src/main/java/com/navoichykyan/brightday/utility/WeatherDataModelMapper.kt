package com.navoichykyan.brightday.utility

import com.navoichykyan.brightday.data.models.WeatherDataModel
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class WeatherDataModelMapper : (String) -> MutableList<MutableList<WeatherDataModel>> {

    override fun invoke(jsonData: String): MutableList<MutableList<WeatherDataModel>> {
        val jsonObject = JSONObject(jsonData)
        val jsonCityArray = jsonObject.getJSONObject("city")
        val city = jsonCityArray.getString("name")
        val jsonListArray = jsonObject.getJSONArray("list")
        val listWeatherList = mutableListOf<MutableList<WeatherDataModel>>()
        var weatherList = mutableListOf<WeatherDataModel>()
        var currentDay = SimpleDateFormat("EEEE").format(Date())
        if (jsonListArray.length() != 0) {
            for (i in 0 until jsonListArray.length()) {
                val jsonWeatherArray = jsonListArray.getJSONObject(i).getJSONArray("weather")
                val dateText = (parseDate(
                    jsonListArray.getJSONObject(i).getString("dt_txt"),
                    "yyyy-MM-dd HH:mm:ss"
                )?.hours.toString()) + ":00"
                val day = SimpleDateFormat("EEEE").format(
                    parseDate(
                        jsonListArray.getJSONObject(i).getString("dt_txt"),
                        "yyyy-MM-dd HH:mm:ss"
                    )
                )

                val temp = jsonListArray.getJSONObject(i).getJSONObject("main").getString("temp")
                val clouds = jsonListArray.getJSONObject(i).getJSONObject("clouds").getString("all")
                val wind = jsonListArray.getJSONObject(i).getJSONObject("wind").getString("speed")
                val humidity = jsonListArray.getJSONObject(i).getJSONObject("main").getString("humidity")
                val pressure = jsonListArray.getJSONObject(i).getJSONObject("main").getString("pressure")
                val visibility = (jsonListArray.getJSONObject(i).getString("visibility").toDouble() / 1000).toString()
                val dataModel = with(jsonWeatherArray.getJSONObject(0)) {
                    WeatherDataModel(
                        main = getString("main"),
                        description = getString("description"),
                        icon = "https://openweathermap.org/img/wn/" + getString("icon") + "@2x.png",
                        id = getString("id"),
                        temp = temp,
                        city = city,
                        date = dateText,
                        clouds = clouds,
                        wind = wind,
                        humidity = humidity,
                        pressure = pressure,
                        visibility = visibility,
                        day = day
                    )
                }
                if(currentDay == day){
                    weatherList.add(dataModel)
                } else{
                    currentDay = day
                    listWeatherList.add(weatherList)
                    weatherList = mutableListOf()
                    weatherList.add(dataModel)
                }
            }
            listWeatherList.add(weatherList)
            return listWeatherList
        }
        return listWeatherList
    }
}

@Throws(ParseException::class)
fun parseDate(date: String, format: String): Date? {
    val formatter = SimpleDateFormat(format)
    return formatter.parse(date)
}