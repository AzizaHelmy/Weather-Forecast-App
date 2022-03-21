package com.example.weatherforecastapp.ui.alerts.view

import com.example.weatherforecastapp.ui.alerts.model.Alert

interface AlertOnClickListener {
    fun onOptionClicked(alert: Alert)
}