package com.example.weatherforecastapp.data.source.local

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.home.model.Forecast
interface WeatherLocalSource {
    suspend fun getWeatherOfFavPlace(favorite: Forecast)
    fun getAllFavs():LiveData<List<Forecast>>

}