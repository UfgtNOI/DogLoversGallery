package com.example.dogloversgallery

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dogloversgallery.database.AppDatabase
import com.example.dogloversgallery.databinding.ActivityMainBinding
import com.example.dogloversgallery.factory.DogViewModelFactory
import com.example.dogloversgallery.network.RetrofitClient
import com.example.dogloversgallery.repository.DogRepository
import com.example.dogloversgallery.viewmodel.DogViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Инициализация ViewModel с использованием фабрики
    private val viewModel: DogViewModel by viewModels {
        DogViewModelFactory((application as DogLoversGalleryApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.dogImage.observe(this) { imageUrl ->
            Glide.with(this).load(imageUrl).into(binding.imageView)
        }

        binding.nextButton.setOnClickListener {
            viewModel.loadRandomDogImage()
        }

        binding.favoriteButton.setOnClickListener {
            viewModel.dogImage.value?.let { imageUrl ->
                viewModel.addToFavorites(imageUrl)
            }
        }

        // Добавляем обработчик для кнопки "Избранное"
        binding.favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        viewModel.loadRandomDogImage()
    }
}