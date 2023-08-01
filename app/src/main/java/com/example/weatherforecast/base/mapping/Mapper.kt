package com.example.weatherforecast.base.mapping

import com.example.weatherforecast.base.enums.ErrorType


interface Mapper<in K, out V> {
    fun map(data: K, type: ErrorType): V
}