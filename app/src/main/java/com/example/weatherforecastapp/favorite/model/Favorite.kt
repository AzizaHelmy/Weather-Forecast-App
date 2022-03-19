package com.example.weatherforecastapp.favorite.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "favorites")
//@Parcelize
data class Favorite(@PrimaryKey val place: String, val latitude: Double, val longitude: Double) :
    Serializable


