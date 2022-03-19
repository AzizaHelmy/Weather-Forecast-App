package com.example.weatherforecastapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.home.model.Forecast

@Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorit(favorite: Favorite)
    @Delete
    fun deleteFav(favorite: Favorite)
    @Query("select* from favorites")
    fun getAllFavPlaces():LiveData<List<Favorite>>
}