package com.example.weatherforecastapp.home.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.NextDayItemBinding
import com.example.weatherforecastapp.home.model.Daily

class NextDaysAdapter(val daily: ArrayList<Daily>, val context: Context) :
    RecyclerView.Adapter<NextDaysAdapter.NextDaysViewHolder>() {
    private lateinit var binding: NextDayItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NextDaysViewHolder {

        return (NextDaysViewHolder(
            NextDayItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
    }

    override fun onBindViewHolder(holder: NextDaysViewHolder, position: Int) {
        val model = daily[position]
        holder.binding.tvTempItem.text="-79"
        holder.binding.tvTempTypeItem.text="°"
        holder.binding.ivNext7day.setImageResource(R.drawable.storm)
    }

    override fun getItemCount(): Int {
        return daily.size
    }

    //======================================================
    class NextDaysViewHolder(val binding: NextDayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}
