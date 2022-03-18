package com.example.weatherforecastapp.home.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentHomeBinding
import com.example.weatherforecastapp.home.model.Hourly
import com.example.weatherforecastapp.home.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var hoursAdapter: HoursAdapter
    private lateinit var hours: List<Hourly>

    //KTX
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSettings()

    }

    private fun loadSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val units = sharedPreferences.getString("unit", "")
        val language = sharedPreferences.getString("language", "ar")
        val locationUsingGps = sharedPreferences.getBoolean("USE_DEVICE_LOCATION", true)

    }
    //============================================
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        weatherViewModel.getData(
            requireContext(),
            30.621175336675805,
            32.26823826304946,
            "ar",
            "metric"
        )
        weatherViewModel.liveData.observe(requireActivity()) {
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
            binding.tvTempreture.text = temp
            binding.tvDiscription.text = description
            binding.tvHumidityTemp.text = "$humidity %"
            binding.tvPressureUnit.text = "$pressure hpa"
            binding.tvWindSpeedUnit.text = "$windSpeed m/s"
            binding.tvCloudsUnit.text = "$clouds %"
            binding.tvCountry.text = country
            when (icon) {
                "01d" -> binding.ivIcon.setImageResource(R.drawable.icon1)
                "02d" -> binding.ivIcon.setImageResource(R.drawable.icon2)
                "03d" -> binding.ivIcon.setImageResource(R.drawable.icon3)
                "04d" -> binding.ivIcon.setImageResource(R.drawable.icon4)
                "09d" -> binding.ivIcon.setImageResource(R.drawable.storm)
                "10d" -> binding.ivIcon.setImageResource(R.drawable.few_cloud)
                "11d" -> binding.ivIcon.setImageResource(R.drawable.few_clouds)
                "13d" -> binding.ivIcon.setImageResource(R.drawable.storm)
                "50d" -> binding.ivIcon.setImageResource(R.drawable.storm)
                "01n" -> binding.ivIcon.setImageResource(R.drawable.storm)
                "02n" -> binding.ivIcon.setImageResource(R.drawable.scarred)
                "03n" -> binding.ivIcon.setImageResource(R.drawable.sun)
                "04n" -> binding.ivIcon.setImageResource(R.drawable.few_clouds)
                "09n" -> binding.ivIcon.setImageResource(R.drawable.sun)
                "10n" -> binding.ivIcon.setImageResource(R.drawable.storm)
                "11n" -> binding.ivIcon.setImageResource(R.drawable.storm)
                "13n" -> binding.ivIcon.setImageResource(R.drawable.storm)
                "50n" -> binding.ivIcon.setImageResource(R.drawable.storm)
            }
            setUpRecyclerView()
        }

        val date = Date()
        val formatedDate: String =
            SimpleDateFormat("EEE, d MMM yyyy ", Locale.ENGLISH).format(date)
        binding.tvDate.text = formatedDate

        return binding.root

    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvHours.layoutManager = layoutManager
        hoursAdapter = HoursAdapter(hours, requireContext())
        binding.rvHours.adapter = hoursAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv7days.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_homeFragment_to_nextDaysFragment)
            }

        })
    }


}