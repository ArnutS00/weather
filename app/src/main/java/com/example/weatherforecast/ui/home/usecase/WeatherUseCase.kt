package com.example.weatherforecast.ui.home.usecase

import com.example.weatherforecast.base.enums.UnitsType
import com.example.weatherforecast.base.extension.titleCase
import com.example.weatherforecast.base.extension.toDateFormat
import com.example.weatherforecast.base.extension.toDecimalFormat
import com.example.weatherforecast.base.model.WeatherModel
import com.example.weatherforecast.base.provider.CoroutineDispatchersProvider
import com.example.weatherforecast.base.provider.VariablesProvider.DEFAULT_STRING
import com.example.weatherforecast.base.provider.VariablesProvider.FORMAT_NO_DECIMAL
import com.example.weatherforecast.base.provider.VariablesProvider.FORMAT_TWO_DECIMAL_DIGIT
import com.example.weatherforecast.base.provider.VariablesProvider.NA
import com.example.weatherforecast.data.model.response.WeatherResponse
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.di.AppModule
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import javax.inject.Named

class WeatherUseCase @Inject constructor(
    @Named(AppModule.API_KEY) private val appId: String,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val weatherRepository: WeatherRepository) {

    suspend fun execute(cityName : String, unitsType: UnitsType) : WeatherModel {
        return withContext(coroutineDispatchersProvider.io()){
            val response = weatherRepository.getWeather(
                cityName = cityName,
                units = unitsType.unitsName,
                appId = appId
            )
            mapDataResponse(response,unitsType)

        }

    }
    private fun mapDataResponse(response : WeatherResponse,unitsType: UnitsType) : WeatherModel {
        val time = response.dt?.let {
            val dt = Date(it).time.toString()
            dt.toDateFormat("HH:mm")
        } ?:run {
            NA
        }
        return WeatherModel(
            cityName = response.name ?: DEFAULT_STRING,
            time = time,
            pressure = "${response.main?.pressure} hPa",
            weatherDesc = response.weather?.first()?.description?.titleCase() ?: DEFAULT_STRING,
            windSpeed = "${response.wind?.speed?.toDecimalFormat(FORMAT_TWO_DECIMAL_DIGIT)} ${unitsType.windSpeedUnit}",
            humidity = "${response.main?.humidity} %",
            temp = "${response.main?.temp?.toDecimalFormat(FORMAT_NO_DECIMAL)}°",
            tempMin = "${response.main?.tempMin?.toDecimalFormat(FORMAT_NO_DECIMAL)}°",
            tempMax = "${response.main?.tempMax?.toDecimalFormat(FORMAT_NO_DECIMAL)}°",
            cloud = "${response.clouds?.all} %",
            weatherIconURL = "http://openweathermap.org/img/wn/${response.weather?.firstOrNull()?.icon}@4x.png"
        )

    }
}