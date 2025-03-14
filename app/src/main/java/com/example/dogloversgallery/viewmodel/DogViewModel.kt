package com.example.dogloversgallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogloversgallery.database.FavoriteDog
import com.example.dogloversgallery.repository.DogRepository
import kotlinx.coroutines.launch

class DogViewModel(private val repository: DogRepository) : ViewModel() {

    private val _dogImage = MutableLiveData<String>()
    val dogImage: LiveData<String> get() = _dogImage

    val favorites: LiveData<List<FavoriteDog>> = repository.getFavorites()

    fun loadRandomDogImage() {
        viewModelScope.launch {
            try {
                val response = repository.getRandomDogImage()
                _dogImage.value = response.imageUrl
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }

    fun addToFavorites(imageUrl: String) {
        viewModelScope.launch {
            repository.addToFavorites(imageUrl)
        }
    }

    fun removeFromFavorites(imageUrl: String) {
        viewModelScope.launch {
            repository.removeFromFavorites(imageUrl)
        }
    }


}