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

    fun hasNetwork(): Boolean {
        return instance!!.isInternetAvailable(this)
    }

    fun isInternetAvailable(context: Context): Boolean {
        var isConnected: Boolean = false // Initial Value
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }
}