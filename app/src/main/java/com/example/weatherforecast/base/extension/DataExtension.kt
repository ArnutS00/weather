package com.example.weatherforecast.base.extension

import android.annotation.SuppressLint
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date

internal fun Double.toDecimalFormat(pattern: String): String {
    val dec = DecimalFormat(pattern)
    dec.roundingMode = RoundingMode.DOWN
    return dec.format(this)
}

@SuppressLint("SimpleDateFormat")
internal fun String.toDateFormat(format: String): String {
    val formatter = SimpleDateFormat(format)
    val longTimeStamp = this.toLong()
    val dateTimeStamp = if (this.length == 10) {
        Date(longTimeStamp * 1000)
    } else {
        Date(longTimeStamp)
    }
    return formatter.format(dateTimeStamp)

}

fun String.titleCase(): String =
    this.replaceFirstChar { // it: Char
        if (it.isLowerCase())
            it.titlecase()
        else
            it.toString()
    }
