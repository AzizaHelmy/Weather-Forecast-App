package com.example.weatherforecastapp.data.source.local

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.favorite.model.Favorite

interface WeatherLocalSource {
    suspend fun getWeatherOfFavPlace(favorite: Favorite)
    fun getAllFavs(): LiveData<List<Favorite>>
    fun insertToFav(favorite: Favorite)
    fun deleteFav(favorite: Favorite)
}