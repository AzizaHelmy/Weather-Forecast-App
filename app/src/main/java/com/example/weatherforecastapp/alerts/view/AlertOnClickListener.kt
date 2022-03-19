package com.example.weatherforecastapp.alerts.view

import com.example.weatherforecastapp.alerts.model.Alert

interface AlertOnClickListener {
    fun onOptionClicked(alert: Alert)
}