package com.example.weatherforecastapp.home.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentHomeBinding
import com.example.weatherforecastapp.databinding.FragmentNextDaysBinding
import com.example.weatherforecastapp.home.model.Daily
import com.example.weatherforecastapp.home.model.Hours

class NextDaysFragment : Fragment() {
    private lateinit var binding: FragmentNextDaysBinding
    private lateinit var nextDaysAdapter: NextDaysAdapter
    //private lateinit var daily: List<Daily>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNextDaysBinding.inflate(LayoutInflater.from(context), container, false)
        setUpRecyclerView()
        return binding.root
    }
    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvNext7days.layoutManager = layoutManager
//        daily= listOf(Daily(22, R.drawable.storm,"3:00"),
//            Hours(22, R.drawable.storm,"3:00"),
//            Hours(22, R.drawable.storm,"5:00"),
//            Hours(22, R.drawable.storm,"6:00"),
//            Hours(22, R.drawable.storm,"7:00"))
        nextDaysAdapter = NextDaysAdapter(ArrayList(), requireContext())
        binding.rvNext7days.adapter = nextDaysAdapter
    }

}