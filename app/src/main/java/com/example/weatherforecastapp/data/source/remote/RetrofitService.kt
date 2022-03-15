package com.example.weatherforecastapp.data.source.remote

import com.example.weatherforecastapp.home.model.Current
import com.example.weatherforecastapp.home.model.Forecast
import com.example.weatherforecastapp.home.model.Weather
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitService {
@GET("data/2.5/onecall?lat=30.621175336675805&lon=32.26823826304946&exclude=hourly.dt,daily.dt&appid=3ad5f88f2ce3886821ab4d21d66f843a")
suspend fun getCurrentWeatherByLatAndLon():Response<Forecast>
}