package com.example.weatherforecastapp.data.source.remote

import com.example.weatherforecastapp.home.model.Forecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "3ad5f88f2ce3886821ab4d21d66f843a"

interface RetrofitService {

    //@GET("data/2.5/onecall?lat=30.621175336675805&lon=32.26823826304946&exclude=hourly.dt,daily.dt&lang=ar&units=metric&appid=3ad5f88f2ce3886821ab4d21d66f843a")
    @GET("data/2.5/onecall?")
    suspend fun getCurrentWeatherByLatAndLon(
        @Query("lat") lat: Double=30.621175336675805,
        @Query("lon") lon: Double=32.26823826304946,
        @Query("lang") lang: String="ar",
        @Query("units") units: String="metric",
        @Query("exclude") exclude: String = "minutely",
        @Query("appid") appid: String = API_KEY
    ): Response<Forecast>
}