package com.example.weatherforecast.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.base.BaseViewModel
import com.example.weatherforecast.base.enums.ErrorType
import com.example.weatherforecast.base.enums.UnitsType
import com.example.weatherforecast.base.model.ResponseState
import com.example.weatherforecast.base.model.WeatherModel
import com.example.weatherforecast.base.provider.CoroutineDispatchersProvider
import com.example.weatherforecast.ui.home.usecase.WeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val weatherUseCase: WeatherUseCase,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider
) : BaseViewModel(application) {

    val weatherDataLiveData by lazy { MutableLiveData<ResponseState<WeatherModel>>() }

    fun loadWeatherData(cityName: String, unitsType: UnitsType) {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            weatherDataLiveData.value =
                ResponseState.error(handleThrowable(throwable, ErrorType.ERROR_DEFAULT))
        }) {
            val response = weatherUseCase.execute(cityName,unitsType)

            withContext(coroutineDispatchersProvider.main()){
                weatherDataLiveData.value = ResponseState.success(response)
            }
        }
    }

}