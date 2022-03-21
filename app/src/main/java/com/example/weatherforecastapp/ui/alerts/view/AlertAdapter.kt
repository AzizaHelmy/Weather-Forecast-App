package com.example.weatherforecastapp.ui.alerts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.ui.alerts.model.Alert
import com.example.weatherforecastapp.databinding.AlertItemBinding

class AlertAdapter(
    val alerts: List<Alert>,
    val context: Context,
    private val alertOnClickListener: AlertOnClickListener
) :
    RecyclerView.Adapter<AlertAdapter.AlertsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertsViewHolder {

        return (AlertsViewHolder(
            AlertItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ))
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        val alerts = alerts[position]
        holder.binding.tvItemStartTime.text = alerts.startTime.toString()
        holder.binding.tvEndTime.text = alerts.endTime.toString()
        holder.binding.tvStartDate.text = alerts.startDate.toString()
        holder.binding.tvEndDate.text=alerts.endDate.toString()
        holder.binding.ivOption.setOnClickListener {
            alertOnClickListener.onOptionClicked(alerts)
        }
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    //======================================================
    class AlertsViewHolder(val binding: AlertItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}
