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
//utility method
    @SuppressLint("SimpleDateFormat")
    fun convertFromUnixToTime(time: Long?): String {
        if(time != null) {
            val sdf = SimpleDateFormat("HH:mm aaa")
            val date = Date(time * 1000)
            return sdf.format(date)
        }
        return "00:00"
    }
    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        val hourly = hours[position]
        holder.binding.tvHour.text=convertFromUnixToTime(hourly.dt.toLong())
        holder.binding.tvHourTemp.text=hourly.temp.toString()
        holder.binding.ivHourIcon.setImageResource(R.drawable.windo)
    }

    override fun getItemCount(): Int {
        return hours.size
    }

    //======================================================
    class HoursViewHolder(val binding: HoursItemBinding) : RecyclerView.ViewHolder(binding.root)

}
