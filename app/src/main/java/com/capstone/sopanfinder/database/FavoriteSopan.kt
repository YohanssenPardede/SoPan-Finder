package com.capstone.sopanfinder.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_sopan")
@Parcelize
data class FavoriteSopan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "result")
    var result: String? = "",

    @ColumnInfo(name = "name_sopan")
    var nameSopan: String? = "",

    @ColumnInfo(name = "cell_type")
    var cellType: String? = "",

    @ColumnInfo(name = "power_output")
    var powerOutput: String? = "",

    @ColumnInfo(name = "efficiency")
    var efficiency: String = "",

    @ColumnInfo(name = "dimensions")
    var dimensions: String? = "",

    @ColumnInfo(name = "weight")
    var weight: String? = "",

    @ColumnInfo(name = "link")
    var link: String? = "",

    @ColumnInfo(name = "link_img")
    var linkImg: String? = ""
): Parcelable