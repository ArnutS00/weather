package com.example.weatherforecast.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.weatherforecast.base.enums.ErrorType
import com.example.weatherforecast.base.mapping.ErrorMapper
import com.example.weatherforecast.base.model.ErrorResponse
import java.util.*
import javax.inject.Inject

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var errorMapper: ErrorMapper


    fun handleThrowable(
        throwable: Throwable,
        errorType: ErrorType = ErrorType.ERROR_DEFAULT
    ): ErrorResponse.GeneralError {
        return errorMapper.map(throwable, errorType)
    }

}