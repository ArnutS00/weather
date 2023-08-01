package com.example.weatherforecast.base.model

import com.squareup.moshi.Json

data class ErrorModel(
    @field:Json(name = "cod")
    val cod: String? = null,
    @field:Json(name = "message")
    var message: String? = null

)