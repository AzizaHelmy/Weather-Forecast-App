package com.example.weatherforecastapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class WeatherApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        val theme = ThemeProvider(this).getThemeFromPreferences()
        AppCompatDelegate.setDefaultNightMode(theme)
    }
}