package com.example.weatherforecastapp.utils

import android.content.Context
import androidx.preference.PreferenceManager

class Preferences(var context: Context) {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val units = sharedPreferences.getString("unit", "")!!
    val language = sharedPreferences.getString("language", "ar")!!
    val locationUsingGps = sharedPreferences.getBoolean("USE_DEVICE_LOCATION", true)

}