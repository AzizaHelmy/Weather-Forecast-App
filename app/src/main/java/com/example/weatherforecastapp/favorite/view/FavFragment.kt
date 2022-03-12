package com.example.weatherforecastapp.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentFavBinding

class FavFragment : Fragment(), FavOnClickListener {
    private lateinit var binding: FragmentFavBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavBinding.inflate(LayoutInflater.from(context),container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddPlace.setOnClickListener(object :View.OnClickListener{
            override fun onClick(view: View?) {
                if (view != null) {
                    Navigation.findNavController(view).navigate(R.id.action_favFragment_to_mapsFragment)
                }
            }

        })
    }

}