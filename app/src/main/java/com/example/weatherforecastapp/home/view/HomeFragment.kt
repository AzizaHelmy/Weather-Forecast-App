package com.example.weatherforecastapp.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentHomeBinding
import com.example.weatherforecastapp.home.model.Hours

class HomeFragment : Fragment(),HoursOnClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var hoursAdapter: HoursAdapter
    private lateinit var hours: List<Hours>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(context), container, false)
        setUpRecyclerView()
        return binding.root

    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvHours.layoutManager = layoutManager
        hours= listOf(Hours(22, R.drawable.storm,"3:00"),
            Hours(22, R.drawable.storm,"3:00"),
            Hours(22, R.drawable.storm,"5:00"),
            Hours(22, R.drawable.storm,"6:00"),
            Hours(22, R.drawable.storm,"7:00"))
        hoursAdapter = HoursAdapter(hours, requireContext())
        binding.rvHours.adapter = hoursAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tv7days.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view: View?) {
              Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_nextDaysFragment)
            }

        })
    }

    override fun onItemClicked(hour: Hours) {
        //view model give me the temp of this our
        TODO("Not yet implemented")
    }


}