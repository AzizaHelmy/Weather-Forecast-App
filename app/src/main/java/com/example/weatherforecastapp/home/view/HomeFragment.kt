package com.example.weatherforecastapp.home.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
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
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.HttpResponse
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentHomeBinding
import com.example.weatherforecastapp.home.model.Hourly
import com.example.weatherforecastapp.home.viewmodel.WeatherViewModel
import com.example.weatherforecastapp.utils.Constant
import com.google.android.gms.location.*

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException
import java.io.InputStream
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
    private lateinit var preferences: Preference
    private lateinit var bundle: Bundle
    private lateinit var geocoder: Geocoder

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
            Log.e("TAG", "location$yourLocationLat")

            weatherViewModel.getData(
                requireContext(),
                yourLocationLat,
                yourLocationLon,
                language,
                units
            )


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSettings()


        geocoder = Geocoder(requireContext(), Locale.getDefault())
        val pressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, pressedCallback)
    }


    private fun loadSettings() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        units = sharedPreferences.getString("unit", "")!!
        language = sharedPreferences.getString("language", "en")!!
        // get location and map  here
        val locationUsingGps = sharedPreferences.getBoolean("USE_DEVICE_LOCATION", true)

    }

    //============================================
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)


        return binding.root

    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvHours.layoutManager = layoutManager
        hoursAdapter = HoursAdapter(hours, requireContext())
        binding.rvHours.adapter = hoursAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv7days.setOnClickListener {

            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_nextDaysFragment, bundle)

        }

        getMyLocation()

        preferences = Preference(requireContext())
        //units = preferences
        weatherViewModel.getData(
            requireContext(),
            yourLocationLat,
            yourLocationLon,
            language,
            units
        )
        weatherViewModel.liveData.observe(requireActivity()) {

            val weather = it
            Log.e("TAG", "Weather$weather")
            hours = weather?.hourly ?: emptyList()
            val humidity = weather.current.humidity.toString()
            val windSpeed = weather.current.wind_speed.toString()
            val pressure = weather.current.pressure.toString()
            val clouds = weather.current.clouds.toString()
            val description = weather.current.weather[0].description
            val icon = weather.current.weather[0].icon
            val temp = weather.current.temp.toInt().toString()
            Log.e("TAG", icon)

            bundle = Bundle()
            bundle.putDouble("lat", it.lat)
            bundle.putDouble("lon", it.lon)

//            val args: Bundle = requireArguments()
//            val latFromMap = args.getInt("lat")
//            val lngFromMap = args.getInt("long")
          //Log.e("TAG","my address ${ getLocationInfo(yourLocationLat,yourLocationLon)}")
          val address: Address =
              //geocoder.getFromLocation(30.621175336675805, 32.26823826304946,1).firstOrNull()?:return@observe
             geocoder.getFromLocation(yourLocationLat, yourLocationLon,1).firstOrNull()?:return@observe
           //geocoder.getFromLocation(yourLocationLat, yourLocationLon, 1)[0]
            val adminArea = address.adminArea
            binding.tvTempreture.text = temp
            binding.tvDiscription.text = description
            binding.tvHumidityTemp.text = "$humidity %"
            binding.tvPressureUnit.text = "$pressure hpa"
            binding.tvWindSpeedUnit.text = "$windSpeed m/s"
            binding.tvCloudsUnit.text = "$clouds %"
//            if (temp.toInt() > 32) {
//                binding.tvUnit.text = "F"
//            }
            binding.tvCountry.text =adminArea
            when (icon) {
                "01d" -> binding.ivIcon.setImageResource(R.drawable.sun)
                "02d" -> binding.ivIcon.setImageResource(R.drawable.few_cloudy)
                "03d" -> binding.ivIcon.setImageResource(R.drawable.clouds)
                "04d" -> binding.ivIcon.setImageResource(R.drawable.icon1)
                "09d" -> binding.ivIcon.setImageResource(R.drawable.shower_rain)
                "10d" -> binding.ivIcon.setImageResource(R.drawable.rainy)
                "11d" -> binding.ivIcon.setImageResource(R.drawable.thunderstorm)
                "13d" -> binding.ivIcon.setImageResource(R.drawable.snow)
                "50d" -> binding.ivIcon.setImageResource(R.drawable.icon2)
                "01n" -> binding.ivIcon.setImageResource(R.drawable.sun)
                "02n" -> binding.ivIcon.setImageResource(R.drawable.scarred)
                "03n" -> binding.ivIcon.setImageResource(R.drawable.clouds)
                "04n" -> binding.ivIcon.setImageResource(R.drawable.icon2)
                "09n" -> binding.ivIcon.setImageResource(R.drawable.rainy)
                "10n" -> binding.ivIcon.setImageResource(R.drawable.rain)
                "11n" -> binding.ivIcon.setImageResource(R.drawable.thunderstorm)
                "13n" -> binding.ivIcon.setImageResource(R.drawable.snow)
                "50n" -> binding.ivIcon.setImageResource(R.drawable.icon2)
            }
            setUpRecyclerView()
        }

        val date = Date()
        val formatedDate: String =
            SimpleDateFormat("EEE, d MMM yyyy ").format(date)
        binding.tvDate.text = formatedDate
    }

    //=========================================================
    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                val locationRequest = LocationRequest.create()
                locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                locationRequest.interval = 0
                locationRequest.fastestInterval = 0
                locationRequest.numUpdates = 1

                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                fusedLocationClient.requestLocationUpdates(
                    locationRequest, locationCallback,
                    Looper.getMainLooper())
//                ).addOnCompleteListener {
//                    Log.e("TAG","${it.result}")
//                }

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
    private fun requestPermisssion() {
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
                getMyLocation()
            }
        }
    }
    //===========================
}