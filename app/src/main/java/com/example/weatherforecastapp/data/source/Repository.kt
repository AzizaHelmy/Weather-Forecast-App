package com.example.weatherforecastapp.data.source

import WeatherRemoteDataSource
import android.app.Application
import androidx.lifecycle.lifecycleScope
import com.example.weatherforecastapp.data.source.local.WeatherLocalDataSource
import com.example.weatherforecastapp.data.source.remote.RetrofitFactory
import com.example.weatherforecastapp.data.source.remote.RetrofitService
import kotlinx.coroutines.*

//Dependancy injection
//Dependancy inversion

class Repository(
    private val remoteDataSource: WeatherRemoteDataSource
    //private val localDataSource: WeatherLocalDataSource,
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

    fun getCurrentWeather() {
        CoroutineScope(Dispatchers.IO).launch {
            val weatherCall = RetrofitFactory.getInstance().create(RetrofitService::class.java)
            val response = weatherCall.getCurrentWeatherByLatAndLon()
            if (response.isSuccessful) {
                val weatherResponse = response.body()
               remoteDataSource.getCurrentWeather(weatherResponse!!)
            }

        }

    }
}