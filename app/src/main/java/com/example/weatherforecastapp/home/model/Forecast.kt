package com.example.weatherforecastapp.home.model

import androidx.room.Entity
import androidx.room.Ignore
import com.example.weatherforecastapp.alerts.model.Alert
//@Entity(tableName = "forecast")
data class Forecast(
    val alerts: List<Alert>,
    val current: Current,
    @Ignore
    val daily: List<Daily>,
    @Ignore
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    @Ignore
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
)