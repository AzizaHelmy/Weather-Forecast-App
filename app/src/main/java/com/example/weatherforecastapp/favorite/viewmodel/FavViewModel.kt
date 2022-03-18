package com.example.weatherforecastapp.favorite.viewmodel

import WeatherRemoteSource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
import com.example.weatherforecastapp.home.model.Forecast

class FavViewModel :ViewModel(),WeatherRemoteSource,WeatherLocalSource{
    private var favMutableLiveData= MutableLiveData<Forecast>()
    val favliveData:LiveData<Forecast> = favMutableLiveData

    var repo = Repository(this,this)
    override suspend fun getWeatherOfFavPlace(favorite: Forecast) {
      return favMutableLiveData.postValue(favorite)
    }

    override fun getAllFavs(): LiveData<List<Forecast>> {
        TODO("Not yet implemented")
    }


    override suspend fun getCurrentWeather(model: Forecast) {
        TODO("Not yet implemented")
    }
}