package com.example.weatherforecast.data.model.response


import com.squareup.moshi.Json

data class ForecastResponse(
    @field:Json(name="city")
    var city: City?,
    @field:Json(name="cnt")
    var cnt: Int?,
    @field:Json(name="cod")
    var cod: String?,
    @field:Json(name="list")
    var list: List<Result?>?,
    @field:Json(name="message")
    var message: Int?
) {
    data class City(
        @field:Json(name="coord")
        var coord: Coord?,
        @field:Json(name="country")
        var country: String?,
        @field:Json(name="id")
        var id: Int?,
        @field:Json(name="name")
        var name: String?,
        @field:Json(name="population")
        var population: Int?,
        @field:Json(name="sunrise")
        var sunrise: Int?,
        @field:Json(name="sunset")
        var sunset: Int?,
        @field:Json(name="timezone")
        var timezone: Int?
    ) {
        data class Coord(
            @field:Json(name="lat")
            var lat: Double?,
            @field:Json(name="lon")
            var lon: Double?
        )
    }

    data class Result (
        @field:Json(name="clouds")
        var clouds: Clouds?,
        @field:Json(name="dt")
        var dt: Long?,
        @field:Json(name="dt_txt")
        var dtTxt: String?,
        @field:Json(name="main")
        var main: Main?,
        @field:Json(name="pop")
        var pop: Double?,
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

        data class Main(
            @field:Json(name="feels_like")
            var feelsLike: Double?,
            @field:Json(name="grnd_level")
            var grndLevel: Int?,
            @field:Json(name="humidity")
            var humidity: Int?,
            @field:Json(name="pressure")
            var pressure: Int?,
            @field:Json(name="sea_level")
            var seaLevel: Int?,
            @field:Json(name="temp")
            var temp: Double?,
            @field:Json(name="temp_kf")
            var tempKf: Double?,
            @field:Json(name="temp_max")
            var tempMax: Double?,
            @field:Json(name="temp_min")
            var tempMin: Double?
        )

        data class Sys(
            @field:Json(name="pod")
            var pod: String?
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
            var deg: Int?,
            @field:Json(name="gust")
            var gust: Double?,
            @field:Json(name="speed")
            var speed: Double?
        )
    }
}