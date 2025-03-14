package com.example.dogloversgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogloversgallery.R
import com.example.dogloversgallery.database.FavoriteDog

class FavoriteDogAdapter(
    private val favoriteDogs: List<FavoriteDog>,
    private val onItemClick: (FavoriteDog) -> Unit
) : RecyclerView.Adapter<FavoriteDogAdapter.FavoriteDogViewHolder>() {

    inner class FavoriteDogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dogImageView: ImageView = itemView.findViewById(R.id.dogImageView)

        fun bind(favoriteDog: FavoriteDog) {
            Glide.with(itemView.context)
                .load(favoriteDog.imageUrl)
                .into(dogImageView)

            itemView.setOnClickListener {
                onItemClick(favoriteDog)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteDogViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_dog, parent, false)
        return FavoriteDogViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteDogViewHolder, position: Int) {
        holder.bind(favoriteDogs[position])
    }

    override fun getItemCount(): Int = favoriteDogs.size
}