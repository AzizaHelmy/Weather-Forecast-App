package com.example.weatherforecastapp.data.source.local

import android.content.Context
import androidx.room.*
import com.example.weatherforecastapp.home.model.Forecast
import com.example.weatherforecastapp.utils.Converters

//@Database(entities = [Forecast::class], version = 1, exportSchema = false)
@TypeConverters(value = [Converters::class])

abstract class WeatherDB : RoomDatabase() {

    abstract fun weatherDao(): WeatherDAO

    companion object {
        fun getInstance(context: Context): WeatherDB {
            return Room.databaseBuilder(
                context.applicationContext,
                WeatherDB::class.java,
                "Weather.db"
            ).build()

        }
    }

}