package com.example.weatherforecastapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatDelegate

class WeatherApplication : Application() {


    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }
        val theme = ThemeProvider(this).getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)
    }

    companion object {
        private var instance: WeatherApplication? = null
        fun getInstance(): WeatherApplication? {
            return instance
        }
    }
}