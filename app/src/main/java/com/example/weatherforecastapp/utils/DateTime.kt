package com.example.weatherforecastapp.utils

import android.annotation.SuppressLint
import java.text.DateFormat
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

        fun getCurrentDate():String {
            val c: Calendar = Calendar.getInstance()
            return  DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun getCurrentTime():String{
            val c: Calendar = Calendar.getInstance()
            val simpleTimeFormat = SimpleDateFormat("hh:mm")
            return  simpleTimeFormat.format(c.time)
        }
    }
}