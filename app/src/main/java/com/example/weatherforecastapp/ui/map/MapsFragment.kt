package com.example.weatherforecastapp.ui.map

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentMapsBinding
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModel
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModelFactory
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
    private lateinit var tvCountryName: TextView
    private lateinit var tvCityName: TextView

    private lateinit var favPlace: Favorite
    private lateinit var buttAdd: Button

    private val favoritesViewModel: FavViewModel by viewModels() {
        FavViewModelFactory(context)
    }


    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        //to make zoom in egypt at the beginning
        val cairo = LatLng(30.033333, 31.233334)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cairo, 5F));

        googleMap.setOnMapClickListener {
            //binding.startContainer.visibility=View.GONE
            // binding.addContainer.visibility = View.VISIBLE
            secondContainer.visibility = View.VISIBLE
            firstContainer.visibility = View.GONE
            location = it
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            googleMap.clear()

            val geocoder = Geocoder(context, Locale.getDefault())
            val address: Address = geocoder.getFromLocation(it.latitude, it.longitude, 1)[0]
            if (it.latitude != null && it.longitude != null) {
                val countryName = address.countryName
                val adminArea = address.adminArea

                Log.e("TAG", "countryName $countryName")
                Log.e("TAG", "locality ${address.locality}")
                Log.e("TAG", "adminArea ${address.adminArea}")
                Log.e("TAG", "featureName ${address.featureName}")
                Log.e("TAG", "postalCode ${address.postalCode}")

                googleMap.addMarker(MarkerOptions().position(location).title("$it"))
                favPlace = Favorite(adminArea, it.latitude, it.longitude)

                tvCityName.text = adminArea
                tvCountryName.text = countryName
            } else {
                Toast.makeText(requireContext(), "select a valid place !!", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        binding = FragmentMapsBinding.inflate(LayoutInflater.from(context), container, false)
//        return binding.root
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstContainer = view.findViewById(R.id.start_container)
        secondContainer = view.findViewById(R.id.add_container)
        tvCountryName = view.findViewById(R.id.tv_country)
        tvCityName = view.findViewById(R.id.tv_place)
        buttAdd = view.findViewById(R.id.butt_add)

        buttAdd.setOnClickListener {
            val args: Bundle = requireArguments()
            val settingFlag = args.getInt("setting")
            if(settingFlag==1){
                val bundle = Bundle()
                bundle.putDouble("lat", location.latitude)
                bundle.putDouble("long", location.longitude)
                Navigation.findNavController(requireView()).navigate(R.id.action_mapsFragment_to_homeFragment,bundle)
            }else{
                favoritesViewModel.insertToFav(favPlace)//don't store my place also PLEASE
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_mapsFragment_to_favFragment)
            }

        }
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }
    //=======================================================

}