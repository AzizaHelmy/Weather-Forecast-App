package com.example.weatherforecastapp.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.HoursItemBinding
import com.example.weatherforecastapp.home.model.Hourly
import com.example.weatherforecastapp.home.model.Hours
import com.example.weatherforecastapp.utils.DateTime
import java.text.SimpleDateFormat
import java.util.*

class HoursAdapter(private val hours: List<Hourly>, val context: Context) :
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
        val hourly = hours[position]
        holder.binding.tvHour.text=DateTime.convertFromUnixToTime(hourly.dt.toLong())
        holder.binding.tvHourTemp.text=hourly.temp.toInt().toString()
        val icon= hourly.weather[0].icon
        when (icon) {
            "01d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.sun)
            "02d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.few_cloudy)
            "03d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.clouds)
            "04d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.icon1)
            "09d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.shower_rain)
            "10d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.rainy)
            "11d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.thunderstorm)
            "13d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.snow)
            "50d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.icon2)
            "01n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.sun)
            "02n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.scarred)
            "03n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.clouds)
            "04n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.icon2)
            "09n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.rainy)
            "10n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.rain)
            "11n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.thunderstorm)
            "13n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.snow)
            "50n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.icon2)
        }
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    //======================================================
    class HoursViewHolder(val binding: HoursItemBinding) : RecyclerView.ViewHolder(binding.root)

}
