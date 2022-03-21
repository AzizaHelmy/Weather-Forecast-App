package com.example.weatherforecastapp.favorite.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavViewModelFactory(private val context: Context?) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(context!!) as T
        } else {
            throw IllegalArgumentException("View Model class Not found")
        }
    }
}