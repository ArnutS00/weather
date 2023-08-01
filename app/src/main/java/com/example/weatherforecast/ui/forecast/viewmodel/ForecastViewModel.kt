package com.example.weatherforecast.ui.forecast.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.base.BaseViewModel
import com.example.weatherforecast.base.enums.ErrorType
import com.example.weatherforecast.base.enums.UnitsType
import com.example.weatherforecast.base.model.ForecastWeatherModel
import com.example.weatherforecast.base.model.ResponseState
import com.example.weatherforecast.base.provider.CoroutineDispatchersProvider
import com.example.weatherforecast.ui.forecast.usecase.ForecastWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    application: Application,
    private val forecastWeatherUseCase: ForecastWeatherUseCase,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider
) : BaseViewModel(application)  {

    val forecastLiveData by lazy { MutableLiveData<ResponseState<List<ForecastWeatherModel>>>() }

    fun loadForecastData(cityName: String, unitsType: UnitsType){
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            forecastLiveData.value =
                ResponseState.error(handleThrowable(throwable, ErrorType.ERROR_DEFAULT))
        }) {
            val response = forecastWeatherUseCase.execute(cityName,unitsType)

            withContext(coroutineDispatchersProvider.main()){
                forecastLiveData.value = ResponseState.success(response)
            }
        }
    }

}