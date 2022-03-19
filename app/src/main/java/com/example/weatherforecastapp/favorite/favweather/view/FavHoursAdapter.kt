package com.example.weatherforecastapp.favorite.favweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.HoursItemBinding
import com.example.weatherforecastapp.home.model.Hourly
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
        val icon= hourly.weather[0].icon
        when (icon) {
            "01d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.sun)
            "02d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.icon2)
            "03d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.sun)
            "04d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.icon4)
            "09d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "10d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.few_cloud)
            "11d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.few_clouds)
            "13d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "50d" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "01n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.night)
            "02n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "03n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "04n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.few_clouds)
            "09n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "10n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "11n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "13n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
            "50n" -> holder.binding.ivHourIcon.setImageResource(R.drawable.storm)
        }
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    //======================================================
    class FavHoursViewHolder(val binding: HoursItemBinding) : RecyclerView.ViewHolder(binding.root)

}
