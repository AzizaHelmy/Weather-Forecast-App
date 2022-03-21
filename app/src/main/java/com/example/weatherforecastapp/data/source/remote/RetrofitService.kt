package com.example.weatherforecastapp.data.source.remote

import com.example.weatherforecastapp.ui.home.model.Forecast
import com.example.weatherforecastapp.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query



interface RetrofitService {

    //@GET("data/2.5/onecall?lat=30.621175336675805&lon=32.26823826304946&exclude=hourly.dt,daily.dt&lang=ar&units=metric&appid=3ad5f88f2ce3886821ab4d21d66f843a")
    @GET("data/2.5/onecall?")
    suspend fun getCurrentWeatherByLatAndLon(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("lang") lang: String="en",
        @Query("units") units: String="metric",
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appid: String = Constant.API_KEY6
    ): Response<Forecast>
}