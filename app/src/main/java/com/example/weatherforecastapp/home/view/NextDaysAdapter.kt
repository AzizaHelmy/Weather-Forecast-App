package com.example.weatherforecastapp.home.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.NextDayItemBinding
import com.example.weatherforecastapp.home.model.Daily
import java.text.SimpleDateFormat
import java.util.*

class NextDaysAdapter(private val daily: List<Daily>, val context: Context) :
    RecyclerView.Adapter<NextDaysAdapter.NextDaysViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextDaysViewHolder {

        return (NextDaysViewHolder(
            NextDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NextDaysViewHolder, position: Int) {
        val model = daily[position]
        holder.binding.tvTempItem.text = model.temp.min.toString()
        holder.binding.tvHumidityTemp.text = "${model.humidity} %"
        holder.binding.tvWindSpeedUnitItem.text = "${model.wind_speed} m/s"
        holder.binding.tvPressureUnitItem.text = "${model.pressure} hpa"
        holder.binding.tvCloudUnitItem.text = "${model.clouds} %"
        holder.binding.tvDescriptionItem.text = model.weather[0].description

        val icon = model.weather[0].icon
        when (icon) {
            "01d" -> holder.binding.ivNext7day.setImageResource(R.drawable.icon1)
            "02d" -> holder.binding.ivNext7day.setImageResource(R.drawable.icon2)
            "03d" -> holder.binding.ivNext7day.setImageResource(R.drawable.icon3)
            "04d" -> holder.binding.ivNext7day.setImageResource(R.drawable.icon4)
            "09d" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "10d" -> holder.binding.ivNext7day.setImageResource(R.drawable.few_cloud)
            "11d" -> holder.binding.ivNext7day.setImageResource(R.drawable.few_clouds)
            "13d" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "50d" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "01n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "02n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "03n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "04n" -> holder.binding.ivNext7day.setImageResource(R.drawable.few_clouds)
            "09n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "10n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "11n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "13n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
            "50n" -> holder.binding.ivNext7day.setImageResource(R.drawable.storm)
        }
        @SuppressLint("SimpleDateFormat")
        fun convertFromUnixToTime(time: Long?): String {
            if (time != null) {
                val sdf = SimpleDateFormat("EEEE")
                val date = Date(time * 1000)
                return sdf.format(date)
            }
            return "00:00"
        }
        when (position) {
            0 -> {
                holder.binding.tvDayItem.text = "Today"
            }
            1 -> {
                holder.binding.tvDayItem.text = "Tomorrow"
            }
            else -> {
                holder.binding.tvDayItem.text = convertFromUnixToTime(model.dt.toLong())
            }
        }


    }

    override fun getItemCount(): Int {
        return daily.size
    }

    //======================================================
    class NextDaysViewHolder(val binding: NextDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}
