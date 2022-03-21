package com.example.weatherforecastapp.data.source

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.ui.alerts.model.Alert
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
import com.example.weatherforecastapp.data.source.remote.RetrofitFactory
import com.example.weatherforecastapp.data.source.remote.RetrofitService
import com.example.weatherforecastapp.data.source.remote.WeatherRemoteSource
import com.example.weatherforecastapp.favorite.model.Favorite
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Dependancy injection
//Dependancy inversion

class Repository(
    private val remoteSource: WeatherRemoteSource,
    private val localSource: WeatherLocalSource,
    //private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

//    companion object {
//        private var INSTANCE: Repository? = null
//        fun getRepository(app:Application):Repository{
//            if(INSTANCE==null){
//               // INSTANCE=Repository(WeatherRemoteDataSource,WeatherLocalDataSource)
//            }
//            return INSTANCE?:Repository()
//        }
//    }

    fun getCurrentWeather(
        context: Context,
        lat: Double,
        lon: Double,
        lang: String = "en",
        unit: String = "metric"
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val weatherCall = RetrofitFactory.getInstance().create(RetrofitService::class.java)
            val response = weatherCall.getCurrentWeatherByLatAndLon(lat, lon, lang, unit)
            if (response.isSuccessful) {
                val weatherResponse = response.body()
                remoteSource.getCurrentWeather(weatherResponse!!)
            }

        }
    }
    //=========================================================
     fun isWeatherAlert(lat:Double, lng:Double):Boolean{
        var flag=false
        CoroutineScope(Dispatchers.IO).launch {
            val weatherCall = RetrofitFactory.getInstance().create(RetrofitService::class.java)
            val response = weatherCall.getCurrentWeatherByLatAndLon(lat, lng)
            if (response.isSuccessful) {
                val weatherResponse = response.body()
                remoteSource.getCurrentWeather(weatherResponse!!)
                //lesssa
            }
        }
        return flag
    }
    //===========================================================
    fun getFavs(): LiveData<List<Favorite>> {
        return localSource.getAllFavs()
    }

    fun insertFav(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            localSource.insertToFav(favorite)
        }
    }

    fun deleteFav(favorite: Favorite) {
        CoroutineScope(Dispatchers.IO).launch {
            localSource.deleteFav(favorite)
        }
    }

    fun deleteAlert(alert: Alert) {
        CoroutineScope(Dispatchers.IO).launch{
            localSource.deleteAlert(alert)
        }

    }

    fun insertAlert(alert: Alert) {
        CoroutineScope(Dispatchers.IO).launch {
            localSource.insertAlert(alert)
        }

    }

    fun getAllAlerts(): LiveData<List<Alert>> {
        return localSource.getAllAlerts()
    }
}