package com.example.weatherforecastapp.utils

import androidx.room.TypeConverter
import com.example.weatherforecastapp.alerts.model.Alert
import com.example.weatherforecastapp.home.model.Daily
import com.example.weatherforecastapp.home.model.Hourly
import com.example.weatherforecastapp.home.model.Minutely
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    /*val alerts: List<Alert>,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val minutely: List<Minutely>,*/
    @TypeConverter
    fun stringToAlertList(value: String?): List<Alert?>? {
        val listType = object : TypeToken<ArrayList<Alert?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun AlertListToString(list: List<Alert?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

}