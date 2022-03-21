package com.example.weatherforecastapp.ui.home.model

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Int,//need
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: RainX,
    val snow: Snow,
    val temp: Double,//need
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)