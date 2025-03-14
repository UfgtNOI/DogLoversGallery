package com.example.dogloversgallery.repository

import com.example.dogloversgallery.database.AppDatabase
import com.example.dogloversgallery.database.FavoriteDog
import com.example.dogloversgallery.network.DogApiService
import com.example.dogloversgallery.network.DogResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DogRepository(private val database: AppDatabase, private val apiService: DogApiService) {

    suspend fun getRandomDogImage(): DogResponse {
        return withContext(Dispatchers.IO) {
            apiService.getRandomDogImage()
        }
    }

    suspend fun addToFavorites(imageUrl: String) {
        withContext(Dispatchers.IO) {
            database.favoriteDogDao().insert(FavoriteDog(imageUrl = imageUrl))
        }
    }

    suspend fun removeFromFavorites(imageUrl: String) {
        withContext(Dispatchers.IO) {
            database.favoriteDogDao().deleteByUrl(imageUrl)
        }
    }

    fun getFavorites() = database.favoriteDogDao().getAll()
}