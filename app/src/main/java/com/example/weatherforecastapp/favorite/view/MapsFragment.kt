package com.example.weatherforecastapp.favorite.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentMapsBinding
import com.example.weatherforecastapp.favorite.model.Favorite
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class MapsFragment : Fragment() {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var location: LatLng
    private lateinit var firstContainer: ConstraintLayout
    private lateinit var secondContainer: ConstraintLayout
    private lateinit var favPlace: Favorite
    private lateinit var buttAdd: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private var yourLocationLat: Double = 0.0
    private var yourLocationLon: Double = 0.0

    companion object {
        private const val PERMISSINO_ID = 0
    }

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        //to make zoom in egypt in the beginning
        val cairo = LatLng(30.033333, 31.233334)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cairo, 5F));

        googleMap.setOnMapClickListener {
            //binding.startContainer.visibility=View.GONE
            // binding.addContainer.visibility = View.VISIBLE
            secondContainer.visibility = View.VISIBLE
            firstContainer.visibility = View.GONE
            location = it
            //googleMap.moveCamera(CameraUpdateFactory.zoomTo(18.0f))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            googleMap.clear()
            Log.e("TAG", "lat" + it.latitude)
            Log.e("TAG", "lng" + it.longitude)

            val geocoder = Geocoder(context, Locale.getDefault())
            val address: Address = geocoder.getFromLocation(it.longitude, it.latitude, 1)[0]
            val countryName = address.countryName
            val locality = address.locality

            Log.e("TAG", "countryName $countryName")
            Log.e("TAG", "locality $locality")
            googleMap.addMarker(MarkerOptions().position(location).title("Marker "))
            favPlace = Favorite("Cairo", it.latitude, it.longitude)

        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val longtitude = locationResult.lastLocation.altitude
            val ltitude = locationResult.lastLocation.latitude
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mLocationRequest = com.google.android.gms.location.LocationRequest()
        mLocationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1


//        binding = FragmentMapsBinding.inflate(LayoutInflater.from(context), container, false)
//        return binding.root
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstContainer = view.findViewById(R.id.start_container)
        secondContainer = view.findViewById(R.id.add_container)
        buttAdd = view.findViewById(R.id.butt_add)
        buttAdd.setOnClickListener {
            val action = MapsFragmentDirections.actionMapsFragmentToFavFragment(favPlace)
            findNavController().navigate(action)
            //Navigation.findNavController(requireView()).navigate(R.id.action_mapsFragment_to_favFragment)

        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    //============================================
//    private fun isLocationEnabled(): Boolean {
//        var locationManager: LocationManager =
//            getSystemService(LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER
//        )
//    }

    //================================================
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

    //=======================================================
//    @SuppressLint("MissingPermission")
//    private fun getMyLocation() {
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//                fusedLocationClient.requestLocationUpdates(
//                    locationRequest, locationCallback,
//                    Looper.myLooper()!!
//                )
//
//            } else {
//                Toast.makeText(requireContext(), "Turn on location", Toast.LENGTH_LONG).show()
//                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//        } else {
//            requestPermisssion()
//        }
//    }

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

    //========================================================
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