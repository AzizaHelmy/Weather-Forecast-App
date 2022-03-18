package com.example.weatherforecastapp.data.source

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.home.model.Forecast

interface RepositoryInterface {
    suspend fun getCurrentWeather()
    fun getFavs(): LiveData<List<Forecast>>
}