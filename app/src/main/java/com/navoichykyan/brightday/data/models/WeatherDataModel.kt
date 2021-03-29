package com.navoichykyan.brightday.data.models

data class WeatherDataModel(
    val main: String,
    val description: String,
    val icon: String,
    val date: String,
    val temp: String,
    val city: String,
    val id: String,
    val clouds: String,
    val wind: String,
    val humidity: String,
    val pressure: String,
    val visibility: String,
    val day: String
)