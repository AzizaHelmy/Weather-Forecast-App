package com.example.weatherforecastapp.data.source.remote

import com.example.weatherforecastapp.ui.home.model.Forecast
import retrofit2.Response

interface WeatherRemoteSource{
  suspend  fun getCurrentWeather(model: Forecast)
}