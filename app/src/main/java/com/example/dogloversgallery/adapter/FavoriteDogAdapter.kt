package com.example.dogloversgallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogloversgallery.R
import com.example.dogloversgallery.database.FavoriteDog

class FavoriteDogAdapter(
    var favoriteDogs: List<FavoriteDog>,
    private val onItemClick: (FavoriteDog) -> Unit,
    private val onDeleteClick: (FavoriteDog) -> Unit
) : RecyclerView.Adapter<FavoriteDogAdapter.FavoriteDogViewHolder>() {

    inner class FavoriteDogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dogImageView: ImageView = itemView.findViewById(R.id.dogImageView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(favoriteDog: FavoriteDog) {
            // Загрузка изображения с оптимизацией
            Glide.with(itemView.context)
                .load(favoriteDog.imageUrl)
                .thumbnail(0.1f) // Оптимизация - сначала грузим миниатюру
                .into(dogImageView)

            // Клик по изображению - открытие полноэкранного просмотра
            dogImageView.setOnClickListener {
                onItemClick(favoriteDog)
            }

            // Клик по кнопке удаления
            deleteButton.setOnClickListener {
                onDeleteClick(favoriteDog)
            }

            // Долгое нажатие для дополнительных действий
            itemView.setOnLongClickListener {
                // Можно добавить контекстное меню
                true
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

    // Метод для обновления списка
    fun updateList(newList: List<FavoriteDog>) {
        favoriteDogs = newList
        notifyDataSetChanged()
    }
}