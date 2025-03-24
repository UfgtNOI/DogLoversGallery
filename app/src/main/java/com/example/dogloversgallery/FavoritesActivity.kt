package com.example.dogloversgallery

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        val adapter = FavoriteDogAdapter(
            emptyList(),
            onItemClick = { favoriteDog ->
                // Открываем FullscreenImageActivity с передачей URL изображения
                val intent = Intent(this, FullscreenImageActivity::class.java).apply {
                    putExtra(FullscreenImageActivity.EXTRA_IMAGE_URL, favoriteDog.imageUrl)
                }
                startActivity(intent)

            },
            onDeleteClick = { favoriteDog ->
                viewModel.removeFromFavorites(favoriteDog.imageUrl)
            }
        )

        setupRecyclerView(adapter)

        viewModel.favorites.observe(this) { favorites ->
            adapter.updateList(favorites)
        }
    }

    private fun setupRecyclerView(adapter: FavoriteDogAdapter) {
        binding.favoritesRecyclerView.apply {
            layoutManager = if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                GridLayoutManager(this@FavoritesActivity, 2)
            } else {
                LinearLayoutManager(this@FavoritesActivity)
            }
            setHasFixedSize(true)
            this.adapter = adapter
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        (binding.favoritesRecyclerView.adapter as? FavoriteDogAdapter)?.let {
            setupRecyclerView(it)
        }
    }
}