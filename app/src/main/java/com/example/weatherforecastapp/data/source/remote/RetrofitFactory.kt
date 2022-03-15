package com.example.weatherforecastapp.data.source.remote

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

object RetrofitFactory {
    private const val baseUrl = "https://api.openweathermap.org/"
    private const val cashSize = 10*1024*1024 //10M
//    //var directory:File= File()
//    private val cashe:Cache = Cache(directory, cashSize.toLong())
//    private val okHttpClient=OkHttpClient.Builder()
//        .cache(cashe)
//        .build()
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
          //  .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}