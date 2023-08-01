package com.example.weatherforecast.base.model

data class WeatherModel(
    val cityName : String,
    val time : String,
    val pressure : String,
    val weatherDesc : String,
    val windSpeed : String,
    val humidity : String,
    val temp : String,
    val tempMin : String,
    val tempMax : String,
    val cloud : String,
    val weatherIconURL : String
)
