package com.example.weatherforecast.base.enums


enum class UnitsType(
    val unitType: String,
    val unitsName : String,
    val tempUnit : String,
    val windSpeedUnit : String

) {
    CELSIUS(
        "CELSIUS",
        "metric",
        "C",
        "meter/sec"
    ),
    FAHRENHEIT(
        "FAHRENHEIT",
        "imperial",
        "F",
        "miles/hour"
    ),
    UNKNOW(
        "",
        "",
        "",
        ""
    );

}