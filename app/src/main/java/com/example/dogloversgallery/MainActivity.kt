package com.example.dogloversgallery

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.dogloversgallery.databinding.ActivityMainBinding
import com.example.dogloversgallery.viewmodel.DogViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: DogViewModel by viewModels()

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

        viewModel.loadRandomDogImage()
    }
}