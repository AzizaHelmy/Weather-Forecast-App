package com.example.weatherforecastapp.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherforecastapp.data.source.RepositoryInterface
//
//class WeatherViewModelFactory(private val repositoryInterface: RepositoryInterface) :
//    ViewModelProvider.Factory {
//
////    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//////        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
//////            return WeatherViewModel(repositoryInterface) as T
//////        } else {
//////            throw IllegalArgumentException("View Model class Not found")
//////        }
////    }
//}