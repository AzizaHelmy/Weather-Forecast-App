package com.example.weatherforecastapp.alerts.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName ="alerts")
data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    @Ignore
    val sender_name: String,
    @PrimaryKey val start: Int,
    @Ignore
    val tags: List<Any>
)