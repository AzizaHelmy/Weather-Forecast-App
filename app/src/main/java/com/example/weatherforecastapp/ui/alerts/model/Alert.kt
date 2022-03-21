package com.example.weatherforecastapp.ui.alerts.model

import androidx.room.*

@Entity(tableName = "alerts")
data class Alert(
    val description: String,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val event: String="Rain",
    val sender_name: String="Azza",
){

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}