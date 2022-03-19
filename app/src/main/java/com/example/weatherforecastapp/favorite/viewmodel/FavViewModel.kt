package com.example.weatherforecastapp.favorite.viewmodel

import com.example.weatherforecastapp.data.source.remote.WeatherRemoteSource
import android.content.Context
import androidx.lifecycle.*
import com.example.weatherforecastapp.alerts.model.Alert
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.local.ConcretLocalSource
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.home.model.Forecast
import kotlinx.coroutines.launch

class FavViewModel(context : Context) : ViewModel(), WeatherRemoteSource, WeatherLocalSource {
    private var favMutableLiveData = MutableLiveData<List<Favorite>>()
    val favliveData: LiveData<List<Favorite>> = favMutableLiveData
    private var mutableLiveData = MutableLiveData<Forecast>()
    var liveData: LiveData<Forecast> = mutableLiveData

    val concretLocalSource: ConcretLocalSource = ConcretLocalSource( context )
    var repo = Repository(this, concretLocalSource)

    fun getData(context: Context, lat:Double, lon:Double) {
        repo.getCurrentWeather(context,lat,lon)
    }

    override suspend fun getWeatherOfFavPlace(favorite: Favorite) {

    }

    override fun getAllFavs(): LiveData<List<Favorite>> {
        return repo.getFavs()
    }

    override fun insertToFav(favorite: Favorite) {
        viewModelScope.launch {
            repo.insertFav(favorite)
        }
    }

    override fun deleteFav(favorite: Favorite) {
        repo.deleteFav(favorite)
    }

    override fun insertAlert(alert: Alert) {
        TODO("Not yet implemented")
    }

    override fun deleteAlert(alert: Alert) {
        TODO("Not yet implemented")
    }

    override fun updateAlert(alert: Alert) {
        TODO("Not yet implemented")
    }

    override fun getAllAlerts(): LiveData<List<Alert>> {
        TODO("Not yet implemented")
    }

//    override fun insertAlert(alert: Alert) {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteAlert(alert: Alert) {
//        TODO("Not yet implemented")
//    }
//
//    override fun updateAlert(alert: Alert) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getAllAlerts(): LiveData<List<Alert>> {
//        TODO("Not yet implemented")
//    }


    override suspend fun getCurrentWeather(model: Forecast) {

    }
}