package com.example.weatherforecastapp.home.viewmodel


import com.example.weatherforecastapp.data.source.remote.WeatherRemoteSource
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapp.alerts.model.Alert
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.home.model.Forecast

//private val repositoryInterface: RepositoryInterface
 class WeatherViewModel : ViewModel(),
    WeatherRemoteSource,WeatherLocalSource {

    private var mutableLiveData = MutableLiveData<Forecast>()
    var liveData: LiveData<Forecast> = mutableLiveData


    var repo = Repository(this,this)

    override suspend fun getCurrentWeather(model: Forecast) {
        return mutableLiveData.postValue(model)
    }

    fun getData(context: Context, lat:Double, lon:Double, lang:String, unit:String) {
        repo.getCurrentWeather(context,lat,lon,lang,unit)
    }

    override suspend fun getWeatherOfFavPlace(favorite: Favorite) {
        TODO("Not yet implemented")
    }

    override fun getAllFavs(): LiveData<List<Favorite>> {
        TODO("Not yet implemented")
    }

    override fun insertToFav(favorite: Favorite) {

    }

    override fun deleteFav(favorite: Favorite) {

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
}