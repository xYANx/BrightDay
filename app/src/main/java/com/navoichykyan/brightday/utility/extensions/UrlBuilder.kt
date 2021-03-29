package com.navoichykyan.brightday.utility.extensions


private const val appId = "f14ff915bd7010b691e3d72c563e3a45"

fun setUrl(lat: String, lon: String) = "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&units=metric&appid=$appId"

fun setUrl(city: String) = "https://api.openweathermap.org/data/2.5/forecast?q=$city&units=metric&appid=$appId"