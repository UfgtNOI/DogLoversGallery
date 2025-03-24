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
    private val viewModel: DogViewModel by viewModels {
        DogViewModelFactory((application as DogLoversGalleryApplication).repository)
    }
    private var currentImageUrl: String? = null // Сохраняем текущий URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Восстанавливаем изображение при повороте
        if (savedInstanceState != null) {
            currentImageUrl = savedInstanceState.getString("CURRENT_IMAGE_URL")
            currentImageUrl?.let { url ->
                Glide.with(this).load(url).into(binding.imageView)
            }
        }

        viewModel.dogImage.observe(this) { imageUrl ->
            if (currentImageUrl != imageUrl) { // Загружаем только новое изображение
                currentImageUrl = imageUrl
                Glide.with(this).load(imageUrl).into(binding.imageView)
            }
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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentImageUrl?.let {
            outState.putString("CURRENT_IMAGE_URL", it)
        }
    }
}