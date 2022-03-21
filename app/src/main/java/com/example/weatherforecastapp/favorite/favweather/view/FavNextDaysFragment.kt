package com.example.weatherforecastapp.favorite.favweather.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.databinding.FragmentNextDaysBinding
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.home.model.Daily
import com.example.weatherforecastapp.home.viewmodel.WeatherViewModel
import kotlinx.coroutines.Job

class FavNextDaysFragment : Fragment() {
    private lateinit var binding: FragmentNextDaysBinding
    private lateinit var favNextDaysAdapter: FavNextDaysAdapter
    private lateinit var daily: List<Daily>

    //KTX
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNextDaysBinding.inflate(LayoutInflater.from(context), container, false)

        val args: Bundle = requireArguments()
        val favorite = args.getSerializable("nextDayes") as Favorite
        val latitude = favorite.latitude
        val longitude = favorite.longitude
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val units = sharedPreferences.getString("unit", "")!!
        val language = sharedPreferences.getString("language", "en")!!
        val locationUsingGps = sharedPreferences.getBoolean("USE_DEVICE_LOCATION", true)

        weatherViewModel.getData(requireContext(),latitude,longitude,language,units)
        weatherViewModel.liveData.observe(requireActivity()) {
            val weatherResponse = it
            daily = weatherResponse.daily
            setUpRecyclerView()
        }

        return binding.root
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvNext7days.layoutManager = layoutManager
        favNextDaysAdapter = FavNextDaysAdapter(daily, requireContext())
        binding.rvNext7days.adapter = favNextDaysAdapter
    }

}