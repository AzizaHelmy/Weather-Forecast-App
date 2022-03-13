package com.example.weatherforecastapp.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.HoursItemBinding
import com.example.weatherforecastapp.home.model.Hours

class HoursAdapter(val hours: List<Hours>, val context: Context) :
    RecyclerView.Adapter<HoursAdapter.HoursViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {

        return HoursViewHolder(
            HoursItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        val model = hours[position]
        holder.binding.tvHour.text="5:00"
        holder.binding.tvHourTemp.text="-7"
        holder.binding.tvHourTempUnit.text="°"
        holder.binding.ivHourIcon.setImageResource(R.drawable.windo)
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    //======================================================
    class HoursViewHolder(val binding: HoursItemBinding) : RecyclerView.ViewHolder(binding.root)

}
