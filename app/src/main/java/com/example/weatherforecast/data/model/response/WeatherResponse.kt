package com.example.weatherforecast.data.model.response


import com.squareup.moshi.Json

data class WeatherResponse(
    @field:Json(name="base")
    var base: String?,
    @field:Json(name="clouds")
    var clouds: Clouds?,
    @field:Json(name="cod")
    var cod: Int?,
    @field:Json(name="coord")
    var coord: Coord?,
    @field:Json(name="dt")
    var dt: Long?,
    @field:Json(name="id")
    var id: Int?,
    @field:Json(name="main")
    var main: Main?,
    @field:Json(name="name")
    var name: String?,
    @field:Json(name="sys")
    var sys: Sys?,
    @field:Json(name="visibility")
    var visibility: Int?,
    @field:Json(name="weather")
    var weather: List<Weather?>?,
    @field:Json(name="wind")
    var wind: Wind?
) {
    data class Clouds(
        @field:Json(name="all")
        var all: Int?
    )

    data class Coord(
        @field:Json(name="lat")
        var lat: Double?,
        @field:Json(name="lon")
        var lon: Double?
    )

    data class Main(
        @field:Json(name="humidity")
        var humidity: Int?,
        @field:Json(name="pressure")
        var pressure: Int?,
        @field:Json(name="temp")
        var temp: Double?,
        @field:Json(name="temp_max")
        var tempMax: Double?,
        @field:Json(name="temp_min")
        var tempMin: Double?
    )

    data class Sys(
        @field:Json(name="country")
        var country: String?,
        @field:Json(name="id")
        var id: Int?,
        @field:Json(name="message")
        var message: Double?,
        @field:Json(name="sunrise")
        var sunrise: Long?,
        @field:Json(name="sunset")
        var sunset: Long?,
        @field:Json(name="type")
        var type: Int?
    )

    data class Weather(
        @field:Json(name="description")
        var description: String?,
        @field:Json(name="icon")
        var icon: String?,
        @field:Json(name="id")
        var id: Int?,
        @field:Json(name="main")
        var main: String?
    )

    data class Wind(
        @field:Json(name="deg")
        var deg: Double?,
        @field:Json(name="gust")
        var gust: Double?,
        @field:Json(name="speed")
        var speed: Double?
    )
}