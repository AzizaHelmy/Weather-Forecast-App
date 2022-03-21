package com.example.weatherforecastapp.favorite.favplaces.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.FragmentFavBinding
import com.example.weatherforecastapp.favorite.model.Favorite
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModel
import com.example.weatherforecastapp.favorite.viewmodel.FavViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavFragment : Fragment(), FavOnClickListener {
    private lateinit var binding: FragmentFavBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favorits: List<Favorite>
//    private lateinit var placesClient: PlacesClient
//    private lateinit var placeFromMap: Favorite

    //KTX
    private val favViewModel by viewModels<FavViewModel>() {
        FavViewModelFactory(requireContext())
    }
    //private val args:FavFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(view!!).navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, pressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavBinding.inflate(LayoutInflater.from(context), container, false)


        //return  inflater.inflate(R.layout.fragment_fav, container, false)
        return binding.root
    }

    //============================================
    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvFavPlaces.layoutManager = layoutManager
        favoriteAdapter = FavoriteAdapter(favorits, requireContext(), this)
        binding.rvFavPlaces.adapter = favoriteAdapter
    }

    //========================================
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favViewModel.getAllFavs()
        favViewModel.getAllFavs().observe(requireActivity()) {
            favorits = it
            Log.e("TAG", "size:${favorits.size}")
            setUpRecyclerView()
            checkEmptyList()
        }
        binding.fabAddPlace.setOnClickListener { view ->
            if (view != null) {
                Navigation.findNavController(view)
                    .navigate(R.id.action_favFragment_to_mapsFragment)
            }
        }
    }

    //=========================================
    private fun checkEmptyList() {
        if (favorits.isEmpty()) {
            binding.ivFavEmpty.visibility = View.VISIBLE
            binding.tvFavEmpty.visibility = View.VISIBLE
            binding.rvFavPlaces.visibility = View.GONE
        } else {
            binding.ivFavEmpty.visibility = View.GONE
            binding.rvFavPlaces.visibility = View.VISIBLE
            binding.tvFavEmpty.visibility = View.GONE
        }
    }

    //===============================================
    override fun onfavClicked(favorite: Favorite) {

        val bundle = Bundle()
        bundle.putSerializable("favs", favorite)

        Navigation.findNavController(requireView())
            .navigate(R.id.action_favFragment_to_favWeatherFragment, bundle)
    }

    //====================================================
    override fun onviewClicked(favorite: Favorite) {
        favViewModel.deleteFav(favorite)
        showSnackBar(favorite)
        checkEmptyList()
        favoriteAdapter.notifyDataSetChanged()
    }

    private fun showSnackBar(favorite: Favorite) {
        val snackbar = Snackbar.make(binding.favLayout, "Removed from Fav!", Snackbar.LENGTH_LONG)
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.purple_700))
        snackbar.setAction("undo") {
            favViewModel.insertToFav(favorite)
            Toast.makeText(requireContext(), "added Again!", Toast.LENGTH_SHORT).show()
        }.show()
    }

    //=============================================
//    private fun autoCompleteSearch() {
//        val autocompleteFragment = childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
//                as AutocompleteSupportFragment
//        // Specify the types of place data to return.
//        autocompleteFragment.setPlaceFields(
//            listOf(
//                Place.Field.ID,
//                Place.Field.NAME,
//                Place.Field.ADDRESS,
//                Place.Field.PHOTO_METADATAS,
//                Place.Field.LAT_LNG
//            )
//        )
//        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onError(status: Status) {
//                Log.i("TAG", "An error occurred: $status")
//            }
//
//            override fun onPlaceSelected(place: Place) {
//
//                var currentPlace = place
//                currentPlace.name?.let {
//                    //setLocName(it)
//                }
//            }
//        })
//    }
}
