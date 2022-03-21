package com.example.weatherforecastapp.favorite.favweather.view

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentFavWeatherBinding
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModel
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModelFactory
import com.example.weatherforecastapp.home.model.Hourly
import java.text.SimpleDateFormat
import java.util.*


class FavWeatherFragment : Fragment() {
    private lateinit var binding: FragmentFavWeatherBinding
    private lateinit var favhoursAdapter: FavHoursAdapter
    private lateinit var hours: List<Hourly>

    //KTX
    private val favViewModel by viewModels<FavViewModel>() {
        FavViewModelFactory(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavWeatherBinding.inflate(LayoutInflater.from(context), container, false)


        return binding.root
    }

    //================================================
    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvHoursFav.layoutManager = layoutManager
        favhoursAdapter = FavHoursAdapter(hours, requireContext())
        binding.rvHoursFav.adapter = favhoursAdapter
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: Bundle = requireArguments()
        val favorite = args.getSerializable("favs") as Favorite
        val latitude = favorite.latitude
        val longitude = favorite.longitude

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val units = sharedPreferences.getString("unit", "")!!
        val language = sharedPreferences.getString("language", "en")!!
        val locationUsingGps = sharedPreferences.getBoolean("USE_DEVICE_LOCATION", true)
        favViewModel.getData(requireContext(), latitude, longitude,language,units)
        favViewModel.liveData.observe(requireActivity()) {
            val weather = it
            Log.e("TAG", "offffff${it}")
            hours = weather?.hourly ?: emptyList()
            val humidity = weather.current.humidity.toString()
            val windSpeed = weather.current.wind_speed.toString()
            val pressure = weather.current.pressure.toString()
            val clouds = weather.current.clouds.toString()
            val description = weather.current.weather[0].description
            val icon = weather.current.weather[0].icon
            val temp = weather.current.temp.toInt().toString()
            val geocoder = Geocoder(context, Locale.getDefault())
            val address: Address = geocoder.getFromLocation(latitude, longitude, 1)[0]
            val countryName = address.countryName
            val adminArea = address.adminArea

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
            binding.tvCountryFav.text = adminArea
            when (icon) {
                "01d" -> binding.ivIconFav.setImageResource(R.drawable.sun)
                "02d" -> binding.ivIconFav.setImageResource(R.drawable.few_cloud)
                "03d" -> binding.ivIconFav.setImageResource(R.drawable.clouds)
                "04d" -> binding.ivIconFav.setImageResource(R.drawable.windo)
                "09d" -> binding.ivIconFav.setImageResource(R.drawable.shower_rain)
                "10d" -> binding.ivIconFav.setImageResource(R.drawable.rainy)
                "11d" -> binding.ivIconFav.setImageResource(R.drawable.thunderstorm)
                "13d" -> binding.ivIconFav.setImageResource(R.drawable.snow)
                "50d" -> binding.ivIconFav.setImageResource(R.drawable.icon2)
                "01n" -> binding.ivIconFav.setImageResource(R.drawable.sun)
                "02n" -> binding.ivIconFav.setImageResource(R.drawable.few_clouds)
                "03n" -> binding.ivIconFav.setImageResource(R.drawable.clouds)
                "04n" -> binding.ivIconFav.setImageResource(R.drawable.windo)
                "09n" -> binding.ivIconFav.setImageResource(R.drawable.rainy)
                "10n" -> binding.ivIconFav.setImageResource(R.drawable.rain)
                "11n" -> binding.ivIconFav.setImageResource(R.drawable.thunderstorm)
                "13n" -> binding.ivIconFav.setImageResource(R.drawable.snow)
                "50n" -> binding.ivIconFav.setImageResource(R.drawable.icon2)
            }
            setUpRecyclerView()
        }
        val date = Date()
        val formatedDate: String =
            SimpleDateFormat("EEE, d MMM yyyy ").format(date)
        binding.tvDateFav.text = formatedDate

        binding.tv7daysFav.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("nextDayes", favorite)

            Navigation.findNavController(view)
                .navigate(R.id.action_favWeatherFragment_to_favNextDaysFragment, bundle)
        }
    }


}





