package com.capstone.sopanfinder.database

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SopanRepository(application: Application) {
    private val mSopanDao: FavoriteSopanDao

    init {
        val db = SopanDatabase.getDatabase(application)
        mSopanDao = db.favoriteSopanDao()
    }

    fun getAllFavoriteSopan(): LiveData<List<FavoriteSopan>> = mSopanDao.getFavoriteSopan()

    fun insertFavorite(id: Int, result: String, nameSopan: String, cellType: String, powerOutput: String, efficiency: String, weight: String, dimensions: String, link: String, linkImg: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val sopan = FavoriteSopan(id, result, nameSopan, cellType, powerOutput, efficiency, dimensions, weight, link, linkImg)
            mSopanDao.insertFavorite(sopan)
        }
    }

    suspend fun checkSopan(nameSopan: String) = mSopanDao.checkSopan(nameSopan)

    fun removeFavorite(nameSopan: String) {
        CoroutineScope(Dispatchers.IO).launch {
            mSopanDao.removeFavorite(nameSopan)
        }
    }
}