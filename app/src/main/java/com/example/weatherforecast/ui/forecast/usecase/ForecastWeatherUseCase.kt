package com.example.weatherforecast.ui.forecast.usecase

import com.example.weatherforecast.base.enums.UnitsType
import com.example.weatherforecast.base.extension.titleCase
import com.example.weatherforecast.base.extension.toDateFormat
import com.example.weatherforecast.base.extension.toDecimalFormat
import com.example.weatherforecast.base.model.ForecastWeatherModel
import com.example.weatherforecast.base.provider.CoroutineDispatchersProvider
import com.example.weatherforecast.base.provider.VariablesProvider
import com.example.weatherforecast.base.provider.VariablesProvider.DEFAULT_STRING
import com.example.weatherforecast.data.model.response.ForecastResponse
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.di.AppModule
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject
import javax.inject.Named

class ForecastWeatherUseCase @Inject constructor(
    @Named(AppModule.API_KEY) private val appId: String,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val weatherRepository: WeatherRepository
) {
    suspend fun execute(cityName: String, unitsType: UnitsType): List<ForecastWeatherModel> {
        return withContext(coroutineDispatchersProvider.io()) {
            val response = weatherRepository.getForecastWeather(
                cityName = cityName,
                units = unitsType.unitsName,
                appId = appId
            )
            mapDataResponse(response)
        }
    }

    private fun mapDataResponse(response: ForecastResponse): List<ForecastWeatherModel> {
        val listForecastWeather = mutableListOf<ForecastWeatherModel>()
        response.list?.forEach { data ->
            var date = DEFAULT_STRING
            var time = DEFAULT_STRING
            data?.dt?.let {
                val dt = Date(it).time.toString()
                date = dt.toDateFormat("EEEE, MMMM d")
                time = dt.toDateFormat("HH:mm")
            } ?: run {
                date = DEFAULT_STRING
                time = DEFAULT_STRING
            }

            val dateHeader = if (listForecastWeather.find { it.date == date } == null) {
                date
            } else {
                null
            }
            listForecastWeather.add(
                ForecastWeatherModel(
                    date = dateHeader,
                    time = time,
                    weatherDesc = data?.weather?.first()?.description?.titleCase()
                        ?: DEFAULT_STRING,
                    tempMin = "${data?.main?.tempMin?.toDecimalFormat(VariablesProvider.FORMAT_NO_DECIMAL)}°",
                    tempMax = "${data?.main?.tempMax?.toDecimalFormat(VariablesProvider.FORMAT_NO_DECIMAL)}°",
                    weatherIconURL = "http://openweathermap.org/img/wn/${data?.weather?.firstOrNull()?.icon}@2x.png"
                )
            )
        }
        return listForecastWeather
    }
}