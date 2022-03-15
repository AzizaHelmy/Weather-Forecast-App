package com.example.weatherforecastapp.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.databinding.FragmentNextDaysBinding
import com.example.weatherforecastapp.home.model.Daily
import com.example.weatherforecastapp.home.viewmodel.WeatherViewModel
import kotlinx.coroutines.Job

class NextDaysFragment : Fragment() {
    private lateinit var binding: FragmentNextDaysBinding
    private lateinit var nextDaysAdapter: NextDaysAdapter
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
        weatherViewModel.getData()
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
        nextDaysAdapter = NextDaysAdapter(daily, requireContext())
        binding.rvNext7days.adapter = nextDaysAdapter
    }

}