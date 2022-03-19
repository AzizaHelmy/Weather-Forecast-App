package com.example.weatherforecastapp.favorite.favweather.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentFavWeatherBinding
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModel
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModelFactory
import com.example.weatherforecastapp.home.model.Hourly
import com.example.weatherforecastapp.home.view.HoursAdapter
import java.text.SimpleDateFormat
import java.util.*


class FavWeatherFragment : Fragment() {
    private lateinit var binding: FragmentFavWeatherBinding
    private lateinit var hoursAdapter: HoursAdapter
    private lateinit var hours: List<Hourly>

    //KTX
    private val favViewModel by viewModels<FavViewModel>() {
        FavViewModelFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavWeatherBinding.inflate(LayoutInflater.from(context), container, false)

        val args: Bundle = requireArguments()
        val favorite = args.getSerializable("favs") as Favorite
        val latitude = favorite.latitude
        val longitude = favorite.longitude

        favViewModel.getData(requireContext(), latitude, longitude)
        favViewModel.liveData.observe(requireActivity()) {
            val weather = it
            hours = weather?.hourly ?: emptyList()
            val humidity = weather.current.humidity.toString()
            val windSpeed = weather.current.wind_speed.toString()
            val pressure = weather.current.pressure.toString()
            val clouds = weather.current.clouds.toString()
            val description = weather.current.weather[0].description
            val icon = weather.current.weather[0].icon
            val temp = weather.current.temp.toInt().toString()
            val country = it.timezone
            Log.e("TAG", icon)
            binding.tvTempretureFav.text = temp
            binding.tvDiscriptionFav.text = description
            binding.tvHumidityTempFav.text = "$humidity %"
            binding.tvPressureUnitFav.text = "$pressure hpa"
            binding.tvWindSpeedUnitFav.text = "$windSpeed m/s"
            binding.tvCloudsUnitFav.text = "$clouds %"
            if (temp.toInt() > 32) {
                binding.tvUnitFav.text = "F"
            }
            binding.tvCountryFav.text = country
            when (icon) {
                "01d" -> binding.ivIconFav.setImageResource(R.drawable.icon1)
                "02d" -> binding.ivIconFav.setImageResource(R.drawable.icon2)
                "03d" -> binding.ivIconFav.setImageResource(R.drawable.sun)
                "04d" -> binding.ivIconFav.setImageResource(R.drawable.icon4)
                "09d" -> binding.ivIconFav.setImageResource(R.drawable.storm)
                "10d" -> binding.ivIconFav.setImageResource(R.drawable.few_cloud)
                "11d" -> binding.ivIconFav.setImageResource(R.drawable.few_clouds)
                "13d" -> binding.ivIconFav.setImageResource(R.drawable.storm)
                "50d" -> binding.ivIconFav.setImageResource(R.drawable.storm)
                "01n" -> binding.ivIconFav.setImageResource(R.drawable.storm)
                "02n" -> binding.ivIconFav.setImageResource(R.drawable.scarred)
                "03n" -> binding.ivIconFav.setImageResource(R.drawable.sun)
                "04n" -> binding.ivIconFav.setImageResource(R.drawable.few_clouds)
                "09n" -> binding.ivIconFav.setImageResource(R.drawable.sun)
                "10n" -> binding.ivIconFav.setImageResource(R.drawable.storm)
                "11n" -> binding.ivIconFav.setImageResource(R.drawable.storm)
                "13n" -> binding.ivIconFav.setImageResource(R.drawable.storm)
                "50n" -> binding.ivIconFav.setImageResource(R.drawable.storm)
            }
            setUpRecyclerView()
        }
        val date = Date()
        val formatedDate: String =
            SimpleDateFormat("EEE, d MMM yyyy ", Locale.ENGLISH).format(date)
        binding.tvDateFav.text = formatedDate
        return binding.root
    }
//================================================
    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvHoursFav.layoutManager = layoutManager
        hoursAdapter = HoursAdapter(hours, requireContext())
        binding.rvHoursFav.adapter = hoursAdapter
    }


}





