package com.example.weatherforecastapp.map.viewmodel

import com.example.weatherforecastapp.data.source.remote.WeatherRemoteSource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.home.model.Forecast

class MapViewModel: ViewModel(), WeatherRemoteSource,WeatherLocalSource {
    private var favoritePlaces= MutableLiveData<Favorite>()
    private val _favoritePlaces:LiveData<Favorite> = favoritePlaces
    var repo = Repository(this,this)

    override suspend fun getWeatherOfFavPlace(favorite: Favorite) {

    }


    override fun getAllFavs(): LiveData<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override fun insertToFav(favorite: Favorite) {
        TODO("Not yet implemented")
    }

    override fun deleteFav(favorite: Favorite) {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentWeather(model: Forecast) {
        TODO("Not yet implemented")
    }

}