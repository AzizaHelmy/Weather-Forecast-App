package com.example.weatherforecastapp.home.viewmodel


import WeatherRemoteSource
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
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

    override suspend fun getWeatherOfFavPlace(favorite: Forecast) {
        TODO("Not yet implemented")
    }

    override fun getAllFavs(): LiveData<List<Forecast>> {
        TODO("Not yet implemented")
    }
}