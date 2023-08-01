package com.example.weatherforecast.base.model

data class ForecastWeatherModel(
    val date : String?,
    val time : String,
    val weatherDesc : String,
    val tempMin : String,
    val tempMax : String,
    val weatherIconURL : String
)
