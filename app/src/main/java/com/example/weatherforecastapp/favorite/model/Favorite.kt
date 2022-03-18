package com.example.weatherforecastapp.favorite.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//nooo we will use forcaste
//@Entity(tableName = "favorites")
@Parcelize
data class Favorite(val place: String, val latitude: Double, val longitude: Double) : Parcelable


