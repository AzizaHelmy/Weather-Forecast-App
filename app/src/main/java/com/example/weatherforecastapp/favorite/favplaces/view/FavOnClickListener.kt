package com.example.weatherforecastapp.favorite.favplaces.view

import com.example.weatherforecastapp.favorite.model.Favorite

interface FavOnClickListener {
    fun onfavClicked(favorite: Favorite)
    fun onviewClicked(favorite: Favorite)
}