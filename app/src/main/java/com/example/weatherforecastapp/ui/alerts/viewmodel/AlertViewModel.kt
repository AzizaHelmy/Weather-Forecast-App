package com.example.weatherforecastapp.ui.alerts.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherforecastapp.ui.alerts.model.Alert
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.local.ConcretLocalSource
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
import com.example.weatherforecastapp.data.source.remote.WeatherRemoteSource
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.ui.home.model.Forecast

class AlertViewModel(application: Application):AndroidViewModel(application),WeatherRemoteSource,WeatherLocalSource {
    private var alertmutableLiveData = MutableLiveData<Forecast>()
    var alertliveData: LiveData<Forecast> = alertmutableLiveData
    val concretLocalSource: ConcretLocalSource = ConcretLocalSource( application )
    var repo = Repository(this, concretLocalSource)


    override suspend fun getCurrentWeather(model: Forecast) {
        TODO("Not yet implemented")
    }

    override suspend fun getWeatherOfFavPlace(favorite: Favorite) {
        TODO("Not yet implemented")
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

    override fun insertAlert(alert: Alert) {
       repo.insertAlert(alert)
    }

    override fun deleteAlert(alert: Alert) {
        repo.deleteAlert(alert)
    }

    override fun updateAlert(alert: Alert) {
        //repo.up
    }

    override fun getAllAlerts(): LiveData<List<Alert>> {
      return repo.getAllAlerts()
    }

}