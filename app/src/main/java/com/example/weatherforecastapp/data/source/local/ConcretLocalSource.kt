package com.example.weatherforecastapp.data.source.local

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.ui.alerts.model.Alert
import com.example.weatherforecastapp.favorite.model.Favorite

class ConcretLocalSource( context: Context) :
    WeatherLocalSource {
    val database: WeatherDB = WeatherDB.getInstance(context)
    var weatherDAO: WeatherDAO  = database.weatherDao()

    init {

    }

    override suspend fun getWeatherOfFavPlace(favorite: Favorite) {

    }

    override fun getAllFavs(): LiveData<List<Favorite>> {
        return weatherDAO.getAllFavPlaces()
    }

    override fun insertToFav(favorite: Favorite) {
        weatherDAO.insertFavorit(favorite)
    }

    override fun deleteFav(favorite: Favorite) {
        weatherDAO.deleteFav(favorite)
    }

    override fun insertAlert(alert: Alert) {
        weatherDAO.insertAlert(alert)
    }

    override fun deleteAlert(alert: Alert) {
       weatherDAO.deleteAlert(alert)
    }

    override fun updateAlert(alert: Alert) {
        weatherDAO.updateAlert(alert)
    }

    override fun getAllAlerts(): LiveData<List<Alert>> {
        return weatherDAO.getAllAlerts()
    }

}