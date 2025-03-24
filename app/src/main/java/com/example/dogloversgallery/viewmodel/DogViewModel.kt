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

    // Флаг для предотвращения повторной загрузки при повороте
    private var isInitialLoad = true

    val favorites: LiveData<List<FavoriteDog>> = repository.getFavorites()

    fun loadRandomDogImage(forceRefresh: Boolean = false) {
        // Если это не принудительное обновление и изображение уже загружено - пропускаем
        if (!forceRefresh && _dogImage.value != null && !isInitialLoad) {
            return
        }

        viewModelScope.launch {
            try {
                val response = repository.getRandomDogImage()
                _dogImage.value = response.imageUrl
                isInitialLoad = false
            } catch (e: Exception) {
                // Обработка ошибок
                e.printStackTrace()
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

    // Метод для явного обновления изображения
    fun refreshDogImage() {
        loadRandomDogImage(forceRefresh = true)
    }
}