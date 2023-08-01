package com.example.weatherforecast.base.model

import com.example.weatherforecast.base.enums.ErrorType

sealed class ResponseState<out R> {
    data class Success<out T>(val data: T) : ResponseState<T>()
    data class Error(val error: ErrorResponse) : ResponseState<Nothing>()

    companion object {
        fun <T> success(result: T): Success<T> = Success(result)
        fun error(error: ErrorResponse): Error = Error(error)
    }
}

sealed class ErrorResponse {
    data class GeneralError(
        val statusCode: String = "",
        var message: String = "",
        var errorType: ErrorType = ErrorType.ERROR_DEFAULT,
        var exception: Throwable = Throwable()
    ) : ErrorResponse()

    companion object {
        fun generalError(
            code: String,
            message: String,
            errorType: ErrorType,
            exception: Throwable
        ) = GeneralError(code, message, errorType, exception)
    }
}

inline infix fun <T> ResponseState<T>.success(success: (T) -> Unit): ResponseState<T> {
    if (this is ResponseState.Success<T>) {
        success(this.data)
    }
    return this
}

inline infix fun <T> ResponseState<T>.error(error: (ResponseState.Error) -> Unit): ResponseState<T> {
    if (this is ResponseState.Error) {
        error(this)
    }
    return this
}

