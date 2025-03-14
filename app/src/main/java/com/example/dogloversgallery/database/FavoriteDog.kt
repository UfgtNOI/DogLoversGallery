package com.example.dogloversgallery.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_dogs")
data class FavoriteDog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val imageUrl: String
)