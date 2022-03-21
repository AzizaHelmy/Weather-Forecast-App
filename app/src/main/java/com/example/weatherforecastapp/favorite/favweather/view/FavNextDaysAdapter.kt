package com.example.weatherforecastapp.favorite.favweather.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.NextDayItemBinding
import com.example.weatherforecastapp.ui.home.model.Daily
import com.example.weatherforecastapp.utils.DateTime

class FavNextDaysAdapter(private val daily: List<Daily>, val context: Context) :
    RecyclerView.Adapter<FavNextDaysAdapter.NextDaysViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextDaysViewHolder {

        return (NextDaysViewHolder(
            NextDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NextDaysViewHolder, position: Int) {
        val model = daily[position+1]
        holder.binding.tvTempItem.text = model.temp.min.toInt().toString()
        holder.binding.tvHumidityTemp.text = "${model.humidity} %"
        holder.binding.tvWindSpeedUnitItem.text = "${model.wind_speed} m/s"
        holder.binding.tvPressureUnitItem.text = "${model.pressure} hpa"
        holder.binding.tvCloudUnitItem.text = "${model.clouds} %"
        holder.binding.tvDescriptionItem.text = model.weather[0].description
        if (model.temp.min.toInt() <= 32) {
            holder.binding.tvTempTypeItem.text = "C"
            holder.binding.tvWindSpeedUnitItem.text = "${model.wind_speed} m/s"
        } else if (model.temp.min.toInt() in 33..273) {
            holder.binding.tvTempTypeItem.text = "F"
            holder.binding.tvWindSpeedUnitItem.text = "${model.wind_speed} ms/h"
        } else {
            holder.binding.tvTempTypeItem.text = "K"
            holder.binding.tvWindSpeedUnitItem.text = "${model.wind_speed} m/s"
        }
        val icon = model.weather[0].icon
        when (icon) {
            "01d" -> holder.binding.ivNext7day.setImageResource(R.drawable.sun)
            "02d" -> holder.binding.ivNext7day.setImageResource(R.drawable.few_cloud)
            "03d" -> holder.binding.ivNext7day.setImageResource(R.drawable.clouds)
            "04d" -> holder.binding.ivNext7day.setImageResource(R.drawable.windo)
            "09d" -> holder.binding.ivNext7day.setImageResource(R.drawable.shower_rain)
            "10d" -> holder.binding.ivNext7day.setImageResource(R.drawable.rainy)
            "11d" -> holder.binding.ivNext7day.setImageResource(R.drawable.thunderstorm)
            "13d" -> holder.binding.ivNext7day.setImageResource(R.drawable.snow)
            "50d" -> holder.binding.ivNext7day.setImageResource(R.drawable.icon2)
            "01n" -> holder.binding.ivNext7day.setImageResource(R.drawable.sun)
            "02n" -> holder.binding.ivNext7day.setImageResource(R.drawable.few_clouds)
            "03n" -> holder.binding.ivNext7day.setImageResource(R.drawable.clouds)
            "04n" -> holder.binding.ivNext7day.setImageResource(R.drawable.windo)
            "09n" -> holder.binding.ivNext7day.setImageResource(R.drawable.rainy)
            "10n" -> holder.binding.ivNext7day.setImageResource(R.drawable.rain)
            "11n" -> holder.binding.ivNext7day.setImageResource(R.drawable.thunderstorm)
            "13n" -> holder.binding.ivNext7day.setImageResource(R.drawable.snow)
            "50n" -> holder.binding.ivNext7day.setImageResource(R.drawable.icon2)
        }

        holder.binding.tvDateItem.text = DateTime.convertFromUnixToDate(model.dt.toLong())
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val language = sharedPreferences.getString("language", "ar")!!
        when (position+1) {
            1 -> {
                if(language == "ar"){
                    holder.binding.tvDayItem.text = "غدا"
                }else{
                    holder.binding.tvDayItem.text = "Tomorrow"
                }
            }
            2 -> {
                if(language == "ar"){
                    holder.binding.tvDayItem.text = "بعد غد"
                }else{
                    holder.binding.tvDayItem.text = "After Tomorrow"
                }
            }
            else -> {
                holder.binding.tvDayItem.text = DateTime.convertFromUnixToDays(model.dt.toLong())
            }
        }

    }

    override fun getItemCount(): Int {
        return daily.size-1
    }

    //======================================================
    class NextDaysViewHolder(val binding: NextDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}
