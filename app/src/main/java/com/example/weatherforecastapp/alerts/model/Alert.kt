package com.example.weatherforecastapp.alerts.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
//i will customize it
@Entity(tableName = "alerts")
data class Alert(
    val description: String,
    val end: Long,
    val event: String,
    val sender_name: String,
    val start: Long
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}