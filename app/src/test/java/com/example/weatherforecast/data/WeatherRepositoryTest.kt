package com.example.weatherforecast.data

import com.example.weatherforecast.base.enums.UnitsType
import com.example.weatherforecast.data.model.response.WeatherResponse
import com.example.weatherforecast.data.repository.WeatherRepository
import com.example.weatherforecast.data.repository.WeatherRepositoryImpl
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.given
import org.mockito.kotlin.mock
import retrofit2.Response

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {
    private val weatherService = mock<WeatherService>()
    private val appKey = "7cfd73a21e393652cd8e5c4cd6cc949a"
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherRepository = WeatherRepositoryImpl(weatherService)
    }

    @Test
    fun `should get weather success when params is provided`() = runTest {
        // Arrange
        val expectResult = Response.success(mock<WeatherResponse>())

        given(
            weatherService.getWeather(
                cityName = "Bangkok",
                units = UnitsType.CELSIUS.unitsName,
                appId = appKey
            )
        ).willReturn(expectResult.body())

        // Act
        val actualResult = weatherRepository.getWeather( cityName = "Bangkok",
            units = UnitsType.CELSIUS.unitsName,
            appId = appKey)

        // Assert
        assertEquals(expectResult.body(), actualResult)
    }
}