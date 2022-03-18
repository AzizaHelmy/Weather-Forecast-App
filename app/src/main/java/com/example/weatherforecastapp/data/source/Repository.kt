package com.example.weatherforecastapp.data.source

import WeatherRemoteSource
import android.content.Context
import androidx.lifecycle.LiveData
import com.example.weatherforecastapp.data.source.local.WeatherLocalSource
import com.example.weatherforecastapp.data.source.remote.RetrofitFactory
import com.example.weatherforecastapp.data.source.remote.RetrofitService
import com.example.weatherforecastapp.home.model.Forecast
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

    fun getCurrentWeather(context: Context,
                          lat: Double,
                          lon: Double,
                          lang: String,
                          unit: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val weatherCall = RetrofitFactory.getInstance().create(RetrofitService::class.java)
            val response = weatherCall.getCurrentWeatherByLatAndLon(lat, lon, lang, unit)
            if (response.isSuccessful) {
                val weatherResponse = response.body()
                remoteSource.getCurrentWeather(weatherResponse!!)
            }

        }
    }

//    fun getFavs(): LiveData<List<Forecast>> {
//        return localSource.getAllFavs()
//    }
}