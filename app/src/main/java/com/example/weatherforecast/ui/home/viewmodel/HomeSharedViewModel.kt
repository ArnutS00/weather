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
class HomeSharedViewModel @Inject constructor(
    application: Application
) : BaseViewModel(application) {

    private var weatherData : WeatherModel? = null
    private var unitsType : UnitsType = UnitsType.UNKNOW

    fun setWeatherData(weatherDataModel: WeatherModel?){
        weatherData = weatherDataModel
    }

    fun getWeatherData() = weatherData

    fun setUnits(selectUnitsType: UnitsType){
        unitsType = selectUnitsType
    }

    fun getUnitsType() = unitsType
}