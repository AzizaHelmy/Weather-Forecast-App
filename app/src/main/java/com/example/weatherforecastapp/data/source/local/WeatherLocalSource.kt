package com.example.weatherforecastapp.data.source.local

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.alerts.model.Alert
import com.example.weatherforecastapp.favorite.model.Favorite

interface WeatherLocalSource {
    suspend fun getWeatherOfFavPlace(favorite: Favorite)
    fun getAllFavs(): LiveData<List<Favorite>>
    fun insertToFav(favorite: Favorite)
    fun deleteFav(favorite: Favorite)
    fun insertAlert(alert: Alert)
    fun deleteAlert(alert: Alert)
    fun updateAlert(alert: Alert)
    fun getAllAlerts(): LiveData<List<Alert>>
}