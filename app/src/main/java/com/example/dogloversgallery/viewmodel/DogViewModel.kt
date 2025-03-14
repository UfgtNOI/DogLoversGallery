package com.example.dogloversgallery.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogloversgallery.network.RetrofitClient
import kotlinx.coroutines.launch

class DogViewModel : ViewModel() {

    private val _dogImage = MutableLiveData<String>()
    val dogImage: LiveData<String> get() = _dogImage

    fun loadRandomDogImage() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getRandomDogImage()
                _dogImage.value = response.imageUrl
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
}