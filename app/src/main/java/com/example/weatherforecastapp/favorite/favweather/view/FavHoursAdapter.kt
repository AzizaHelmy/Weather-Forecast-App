package com.example.weatherforecastapp.favorite.favweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.HoursItemBinding
import com.example.weatherforecastapp.ui.home.model.Hourly
import com.example.weatherforecastapp.utils.DateTime

class FavHoursAdapter(private val hours: List<Hourly>, val context: Context) :
    RecyclerView.Adapter<FavHoursAdapter.FavHoursViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavHoursViewHolder {

        return FavHoursViewHolder(
            HoursItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavHoursViewHolder, position: Int) {
        val hourly = hours[position]
        holder.binding.tvHour.text= DateTime.convertFromUnixToTime(hourly.dt.toLong())
        holder.binding.tvHourTemp.text=hourly.temp.toInt().toString()
        if (hourly.temp.toInt()<= 32) {
            holder.binding.tvHourTempp.text = "C"
        } else if (hourly.temp.toInt() in 33..273) {
            holder.binding.tvHourTempp.text = "F"
        } else {
            holder.binding.tvHourTempp.text = "K"
        }
        val icon= hourly.weather[0].icon
        when (icon) {
            "01d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.sun)
            "02d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.few_cloud)
            "03d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.clouds)
            "04d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.windo)
            "09d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.shower_rain)
            "10d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.rainy)
            "11d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.thunderstorm)
            "13d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.snow)
            "50d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.icon2)
            "01n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.sun)
            "02n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.few_clouds)
            "03n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.clouds)
            "04n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.windo)
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
    class FavHoursViewHolder(val binding: HoursItemBinding) : RecyclerView.ViewHolder(binding.root)

}
