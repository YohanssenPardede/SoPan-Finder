package com.capstone.sopanfinder.view.result

import android.app.Application
import androidx.lifecycle.ViewModel
import com.capstone.sopanfinder.database.SopanRepository

class ResultViewModel(application: Application) : ViewModel() {
    private val mRepository: SopanRepository = SopanRepository(application)

    fun insertFavorite(id: Int, result: String, nameSopan: String, cellType: String, powerOutput: String, efficiency: String, weight: String, dimensions: String, link: String, linkImg: String) {
        mRepository.insertFavorite(id, result, nameSopan, cellType, powerOutput, efficiency, dimensions, weight, link, linkImg)
    }

    suspend fun checkSopan(nameSopan: String) = mRepository.checkSopan(nameSopan)

    fun removeFavorite(nameSopan: String) {
        mRepository.removeFavorite(nameSopan)
    }
}