package com.example.weatherforecast.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.base.BaseViewModel
import com.example.weatherforecast.base.provider.CoroutineDispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider
) : BaseViewModel(application) {

    private var backToExit : Boolean = false

    fun setBackExit(isBack : Boolean){
        backToExit = isBack
    }

    fun getBackExit() : Boolean = backToExit

    fun processToDefaultExit(){
        viewModelScope.launch {
            delay(2000)
            setBackExit(false)
        }
    }

}