package com.example.weatherforecastapp.ui.home.model

data class Current(
    val clouds: Int,//need
    val dew_point: Double,
    val dt: Int,//need
    val feels_like: Double,
    val humidity: Int,//need
    val pressure: Int,//need
    val rain: Rain,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,//need
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_speed: Double//need
)