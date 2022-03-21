package com.example.weatherforecastapp.favorite.favplaces.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapp.databinding.FavPlacesItemBinding
import com.example.weatherforecastapp.favorite.model.Favorite

class FavoriteAdapter(val favorites: List<Favorite>, val context: Context, val favOnClickListener: FavOnClickListener) :
    RecyclerView.Adapter<FavoriteAdapter.FavsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavsViewHolder {

        return (FavsViewHolder(
            FavPlacesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ))
    }

    override fun onBindViewHolder(holder: FavsViewHolder, position: Int) {
        val model = favorites[position]
        holder.binding.tvFavPlace.text = model.place
        holder.binding.ivDelete.setOnClickListener {
            favOnClickListener.onviewClicked(model)
        }
        holder.itemView.setOnClickListener {
            favOnClickListener.onfavClicked(model)
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    //======================================================
    class FavsViewHolder(val binding: FavPlacesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}
