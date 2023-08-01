package com.example.weatherforecast.data.repository

import com.example.weatherforecast.data.WeatherService
import com.example.weatherforecast.data.model.response.ForecastResponse
import com.example.weatherforecast.data.model.response.WeatherResponse
import javax.inject.Inject

interface WeatherRepository {
    suspend fun getWeather(
        cityName: String,
        units: String,
        appId: String
    ): WeatherResponse

    suspend fun getForecastWeather(
        cityName: String,
        units: String? = null,
        days: Int? = null,
        appId: String
    ): ForecastResponse
}

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
) : WeatherRepository {
    override suspend fun getWeather(
        cityName: String,
        units: String,
        appId: String
    ): WeatherResponse {
        return weatherService.getWeather(cityName, units, appId)
    }

    override suspend fun getForecastWeather(
        cityName: String,
        units: String?,
        days: Int?,
        appId: String
    ): ForecastResponse {
        return weatherService.getForecastWeather(cityName, units, days, appId)
    }

}