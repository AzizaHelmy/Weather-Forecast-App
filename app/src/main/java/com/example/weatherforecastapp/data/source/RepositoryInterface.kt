package com.example.weatherforecastapp.data.source

interface RepositoryInterface {
suspend fun getCurrentWeather()
}