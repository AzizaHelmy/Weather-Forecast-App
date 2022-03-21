package com.example.weatherforecastapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherforecastapp.ui.alerts.model.Alert
import com.example.weatherforecastapp.favorite.model.Favorite

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorit(favorite: Favorite)
    @Delete
    fun deleteFav(favorite: Favorite)
    @Query("select* from favorites")
    fun getAllFavPlaces():LiveData<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlert(alert: Alert)
    @Delete
    fun deleteAlert(alert: Alert)
    @Update
    fun updateAlert(alert: Alert)
    @Query("select* from alerts")
    fun getAllAlerts(): LiveData<List<Alert>>
}