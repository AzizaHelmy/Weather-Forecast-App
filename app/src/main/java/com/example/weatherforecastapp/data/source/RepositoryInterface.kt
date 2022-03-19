package com.example.weatherforecastapp.data.source

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.alerts.model.Alert
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.home.model.Forecast

interface RepositoryInterface {

    suspend fun getCurrentWeather()
    fun getFavs(): LiveData<List<Forecast>>
    fun insertFav(favorite: Favorite)
    fun deleteFav(favorite: Favorite)
    fun deleteAlert(alert: Alert)
    fun insertAlert(alert: Alert)
    fun getAllAlerts():LiveData<List<Alert>>
    suspend fun isWeatherAlert(lat:Double, lng:Double):Boolean
}