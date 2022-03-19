package com.example.weatherforecastapp.alerts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.alerts.model.Alert
import com.example.weatherforecastapp.databinding.AlertItemBinding

class AlertAdapter(val alerts: ArrayList<Alert>, val context: Context) :
    RecyclerView.Adapter<AlertAdapter.AlertsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {

        return (AlertsViewHolder(AlertItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        val alerts=alerts[position]
        holder.binding.tvItemStartTime.text= alerts.start.toString()
        //
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    //======================================================
    class AlertsViewHolder(val binding: AlertItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
