package com.example.dogloversgallery

import android.app.Application
import com.example.dogloversgallery.database.AppDatabase
import com.example.dogloversgallery.network.RetrofitClient
import com.example.dogloversgallery.repository.DogRepository

class DogLoversGalleryApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { DogRepository(database, RetrofitClient.instance) }
}