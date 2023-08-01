package com.example.weatherforecast.data

import com.example.weatherforecast.data.model.response.ForecastResponse
import com.example.weatherforecast.data.model.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("units") units: String? = null,
        @Query("appid") appId: String
    ) : WeatherResponse

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("q") cityName: String,
        @Query("units") units: String? = null,
        @Query("cnt") days : Int? = null,
        @Query("appid") appId: String
    ) : ForecastResponse
}