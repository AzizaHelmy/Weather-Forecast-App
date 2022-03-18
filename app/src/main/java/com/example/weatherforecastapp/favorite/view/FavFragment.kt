package com.example.weatherforecastapp.favorite.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentFavBinding
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModel

class FavFragment : Fragment(), FavOnClickListener {
    private lateinit var binding: FragmentFavBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favorits: List<Favorite>

    //KTX
    private val favViewModel: FavViewModel by viewModels()
    //private val args:FavFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(LayoutInflater.from(context), container, false)
        //favViewModel.getAllFavs()
       // println("from fav ${args.currentFav}")
        favViewModel.favliveData.observe(requireActivity()) {
            val favs = it
            //favorits=favs.F
            setUpRecyclerView()
        }
        return binding.root
    }

    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvFavPlaces.layoutManager = layoutManager
        favoriteAdapter = FavoriteAdapter(favorits, requireContext())
        binding.rvFavPlaces.adapter = favoriteAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddPlace.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (view != null) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_favFragment_to_mapsFragment)
                }
            }
        })
    }

    override fun onfavClicked(favorite: Favorite) {
        //just for testing
        Navigation.findNavController(requireView())
            .navigate(R.id.action_favFragment_to_homeFragment)
    }

}