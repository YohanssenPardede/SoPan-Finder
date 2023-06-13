package com.capstone.sopanfinder.view.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.sopanfinder.database.FavoriteSopan
import com.capstone.sopanfinder.database.SopanRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mRepository: SopanRepository = SopanRepository(application)

    fun getAllFavoriteSopan(): LiveData<List<FavoriteSopan>> = mRepository.getAllFavoriteSopan()
}