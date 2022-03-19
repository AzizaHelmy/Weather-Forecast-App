package com.example.weatherforecastapp.alerts.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    val sender_name: String,
    val start: Int,
    @Ignore
    val tags: List<Any>
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}