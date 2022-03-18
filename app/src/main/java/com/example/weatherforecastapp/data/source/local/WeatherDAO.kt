package com.example.weatherforecastapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherforecastapp.home.model.Forecast

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorit(forecast: Forecast)
    @Delete
    fun deleteFav(forecast: Forecast)
//    @Query("select* from forecast")
//    fun getAllFavPlaces():LiveData<List<Forecast>>
}