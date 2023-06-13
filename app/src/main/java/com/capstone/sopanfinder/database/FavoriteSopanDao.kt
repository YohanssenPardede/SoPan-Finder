package com.capstone.sopanfinder.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteSopanDao {
    @Insert
    suspend fun insertFavorite(favoriteSopan: FavoriteSopan)

    @Query("SELECT * FROM favorite_sopan ORDER BY name_sopan ASC")
    fun getFavoriteSopan() : LiveData<List<FavoriteSopan>>

    @Query("SELECT name_sopan FROM favorite_sopan WHERE name_sopan = :name")
    suspend fun checkSopan(name: String): String

    @Query("DELETE FROM favorite_sopan WHERE favorite_sopan.name_sopan = :name")
    suspend fun removeFavorite(name: String)
}