package com.example.dogloversgallery

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogloversgallery.adapter.FavoriteDogAdapter
import com.example.dogloversgallery.databinding.ActivityFavoritesBinding
import com.example.dogloversgallery.factory.DogViewModelFactory
import com.example.dogloversgallery.viewmodel.DogViewModel

class FavoritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritesBinding
    private val viewModel: DogViewModel by viewModels {
        DogViewModelFactory((application as DogLoversGalleryApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Создание адаптера
        val adapter = FavoriteDogAdapter(
            emptyList(),
            onItemClick = { favoriteDog ->
                // Обработка клика по элементу (например, открытие деталей)
            },
            onDeleteClick = { favoriteDog ->
                viewModel.removeFromFavorites(favoriteDog.imageUrl) // Удаление из избранного
            }
        )

        // Настройка RecyclerView
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.favoritesRecyclerView.adapter = adapter

        // Наблюдение за изменениями в списке избранных изображений
        viewModel.favorites.observe(this) { favorites ->
            adapter.favoriteDogs = favorites
            adapter.notifyDataSetChanged()
        }
    }
}