package com.capstone.sopanfinder.api

import com.google.gson.annotations.SerializedName

data class SopanResponse(

    @field:SerializedName("result")
    val result: String,

    @field:SerializedName("name_sopan")
    val nameSopan: String,

    @field:SerializedName("panel_specification")
    val panel_specification : PanelSpecification,

    @field:SerializedName("link")
    val link: String,

    @field:SerializedName("link_img")
    val linkImg: String,
)

data class PanelSpecification(

    @field:SerializedName("solar_cell_type")
    val solarCellType: String,

    @field:SerializedName("power_output")
    val powerOutput: String,

    @field:SerializedName("efficiency")
    val efficiency: String,

    @field:SerializedName("dimensions")
    val dimensions: String,

    @field:SerializedName("weight")
    val weight: String
)
