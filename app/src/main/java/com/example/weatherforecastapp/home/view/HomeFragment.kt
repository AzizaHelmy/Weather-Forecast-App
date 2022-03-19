package com.example.weatherforecastapp.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import com.example.weatherforecastapp.R
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.databinding.FragmentHomeBinding
import com.example.weatherforecastapp.home.model.Hourly
import com.example.weatherforecastapp.home.viewmodel.WeatherViewModel
import com.google.android.gms.location.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var hoursAdapter: HoursAdapter
    private lateinit var hours: List<Hourly>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var yourLocationLat: Double = 0.0
    private var yourLocationLon: Double = 0.0
    private lateinit var units: String
    private lateinit var language: String

    companion object {
        private const val PERMISSINO_ID = 0
    }

    //KTX
    private val weatherViewModel: WeatherViewModel by viewModels()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            yourLocationLat = locationResult.lastLocation.altitude
            yourLocationLon = locationResult.lastLocation.latitude

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSettings()
//        val pressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                findNavController(view!!).navigate()
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(this, pressedCallback)
    }


    private fun loadSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        units = sharedPreferences.getString("unit", "")!!
        language = sharedPreferences.getString("language", "ar")!!
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
            language,
            "imperial"
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
            setData(temp, description, humidity, pressure, windSpeed, clouds, country, icon)
            setUpRecyclerView()
        }

        val date = Date()
        val formatedDate: String =
            SimpleDateFormat("EEE, d MMM yyyy ", Locale.ENGLISH).format(date)
        binding.tvDate.text = formatedDate

        return binding.root

    }

    @SuppressLint("SetTextI18n")
    private fun setData(
        temp: String,
        description: String,
        humidity: String,
        pressure: String,
        windSpeed: String,
        clouds: String,
        country: String,
        icon: String
    ) {
//        val geocoder = Geocoder(context, Locale.getDefault())
//        val address: List<Address> = geocoder.getFromLocation(yourLocationLat,yourLocationLon, 1)
//        val cityName: String = address[0].getAddressLine(0)
//        val stateName: String = address[0].getAddressLine(1)
//        val countryName: String = address[0].getAddressLine(2)

        binding.tvTempreture.text = temp
        binding.tvDiscription.text = description
        binding.tvHumidityTemp.text = "$humidity %"
        binding.tvPressureUnit.text = "$pressure hpa"
        binding.tvWindSpeedUnit.text = "$windSpeed m/s"
        binding.tvCloudsUnit.text = "$clouds %"
        if ( temp.toInt()>32){
            binding.tvUnit.text="F"
        }
        binding.tvCountry.text = country
        when (icon) {
            "01d" -> binding.ivIcon.setImageResource(R.drawable.icon1)
            "02d" -> binding.ivIcon.setImageResource(R.drawable.icon2)
            "03d" -> binding.ivIcon.setImageResource(R.drawable.sun)
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
        binding.tv7days.setOnClickListener {
            //i will pass lat , long
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_nextDaysFragment)

        }

        getMyLocation()
    }

    //=========================================================
    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                val locationRequest = LocationRequest()
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                locationRequest.interval = 0
                locationRequest.fastestInterval = 0
                locationRequest.numUpdates = 1

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.requestLocationUpdates(
                    locationRequest, locationCallback,
                    Looper.myLooper()!!
                )

            } else {
                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermisssion()
        }
    }

    //============================================
    private fun isLocationEnabled(): Boolean {
        val locationManager =
            context?.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    //=============================================
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    //==============================================
    fun requestPermisssion() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSINO_ID
        )
    }

    //==============================================
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSINO_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                //getLastLocation()
            }
        }
    }
}