package com.capstone.sopanfinder.view.favorite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Favorites(
    val name: String,
    val location: String,
    val photo: Int
) : Parcelable
