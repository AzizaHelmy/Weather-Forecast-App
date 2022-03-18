package com.example.weatherforecastapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateTime {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun convertFromUnixToDate(time: Long?): String {
            if (time != null) {
                val sdf = SimpleDateFormat("EEE, d MMM yyyy")
                val date = Date(time * 1000)
                return sdf.format(date)
            }
            return "00:00"
        }

        @SuppressLint("SimpleDateFormat")
        fun convertFromUnixToDays(time: Long?): String {
            if (time != null) {
                val sdf = SimpleDateFormat("EEEE")
                val date = Date(time * 1000)
                return sdf.format(date)
            }
            return "00:00"
        }

        @SuppressLint("SimpleDateFormat")
        fun convertFromUnixToTime(time: Long?): String {
            if(time != null) {
                val sdf = SimpleDateFormat("hh:00 aaa")
                val date = Date(time * 1000)
                return sdf.format(date)
            }
            return "00:00"
        }
    }
}