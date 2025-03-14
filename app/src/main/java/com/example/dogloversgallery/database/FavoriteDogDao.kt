package com.example.dogloversgallery.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDogDao {
    @Insert
    suspend fun insert(favoriteDog: FavoriteDog)

    @Query("SELECT * FROM favorite_dogs")
    fun getAll(): LiveData<List<FavoriteDog>>

    @Query("DELETE FROM favorite_dogs WHERE imageUrl = :imageUrl")
    suspend fun deleteByUrl(imageUrl: String)
}