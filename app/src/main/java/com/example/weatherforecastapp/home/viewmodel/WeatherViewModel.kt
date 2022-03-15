package com.example.weatherforecastapp.home.viewmodel


import WeatherRemoteDataSource
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapp.data.source.Repository
import com.example.weatherforecastapp.data.source.RepositoryInterface
import com.example.weatherforecastapp.home.model.Forecast

//private val repositoryInterface: RepositoryInterface
class WeatherViewModel : ViewModel(),
    WeatherRemoteDataSource {

    private var mutableLiveData = MutableLiveData<Forecast>()
    var liveData: LiveData<Forecast> = mutableLiveData


    var repo = Repository(this)

    override suspend fun getCurrentWeather(model: Forecast) {
        return mutableLiveData.postValue(model)
    }

    fun getData() {
        repo.getCurrentWeather()
    }
}