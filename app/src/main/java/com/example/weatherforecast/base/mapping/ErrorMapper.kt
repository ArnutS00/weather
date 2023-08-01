package com.example.weatherforecast.base.mapping

import com.example.weatherforecast.base.enums.ErrorType
import com.example.weatherforecast.base.model.ErrorModel
import com.example.weatherforecast.base.model.ErrorResponse
import com.example.weatherforecast.base.provider.VariablesProvider.DEFAULT_STRING
import com.squareup.moshi.Moshi
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

class ErrorMapper @Inject constructor(
    private val moshi: Moshi
) : Mapper<Throwable, ErrorResponse.GeneralError> {

    companion object {
        //Key
        const val KEY_ERROR = "error"

        //Code
        const val ERROR_CODE_DEFAULT = "0"

        //ERROR
        const val ERROR_TIMEOUT = "timeout"
        const val ERROR_MAPPING_ADAPTER_FAILED = "Mapping Adapter Failed"
        const val ERROR_UNKNOWN_BODY_STR = "Unknown ErrorStr"
    }

    override fun map(
        data: Throwable,
        type: ErrorType
    ): ErrorResponse.GeneralError {
        return when (data) {
            is HttpException -> {
                handleHttpException(data, type)
            }
            else -> {
                val errorMessage = data.message ?: DEFAULT_STRING
                generateGeneralError(ERROR_CODE_DEFAULT, errorMessage, type, data)
            }
        }
    }

    private fun handleHttpException(
        data: HttpException,
        type: ErrorType
    ): ErrorResponse.GeneralError {
        val errorStr = data.response()?.errorBody()?.string()
        return errorStr?.let {
            try {
                handleErrorResult(it, data, type)
            } catch (ex: Exception) {
                try {
                    val jsonObjectStr = JSONObject(it).getJSONObject(KEY_ERROR).toString()
                    handleErrorResult(jsonObjectStr, data, type)
                } catch (ex: Exception) {
                    generateGeneralError(
                        ERROR_CODE_DEFAULT,
                        ex.message.toString(),
                        type,
                        data
                    )
                }
            }
        } ?: generateGeneralError(
            ERROR_CODE_DEFAULT,
            ERROR_UNKNOWN_BODY_STR,
            type,
            data
        )
    }

    private fun handleErrorResult(
        errorStr: String,
        data: HttpException,
        type: ErrorType,
    ): ErrorResponse.GeneralError {
        val result = moshi.adapter(ErrorModel::class.java)
            .fromJson(errorStr)
        return result?.let { error ->

            val statusCode = error.cod ?: data.code()
            generateGeneralError(
                statusCode.toString(),
                error.message?: DEFAULT_STRING,
                type,
                data
            )
        } ?: generateGeneralError(
            ERROR_CODE_DEFAULT,
            ERROR_MAPPING_ADAPTER_FAILED,
            type,
            data
        )
    }

    private fun generateGeneralError(
        code: String,
        error: String,
        type: ErrorType,
        throwable: Throwable
    ): ErrorResponse.GeneralError {
        return ErrorResponse.generalError(code, error, type, throwable)
    }
}


