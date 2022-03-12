package com.example.weatherforecastapp.home.model

import com.example.weatherforecastapp.alerts.model.Alert

data class Forecast(
    val alerts: List<Alert>,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)