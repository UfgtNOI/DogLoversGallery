package com.example.dogloversgallery.network

import retrofit2.http.GET
import com.example.dogloversgallery.network.DogResponse

interface DogApiService {
    @GET("breeds/image/random")
    suspend fun getRandomDogImage(): DogResponse
}