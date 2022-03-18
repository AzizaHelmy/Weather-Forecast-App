package com.example.weatherforecastapp.data.source.local

import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.home.model.Forecast

class ConcretLocalSource:WeatherLocalSource {
    override suspend fun getWeatherOfFavPlace(favorite: Forecast) {
       TODO("Not yet implemented")
    }

   override fun getAllFavs(): LiveData<List<Forecast>> {
       TODO("Not yet implemented")
   }
}