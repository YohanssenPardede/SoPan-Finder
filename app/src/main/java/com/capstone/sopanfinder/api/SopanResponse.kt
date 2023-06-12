package com.capstone.sopanfinder.api

import com.google.gson.annotations.SerializedName

data class SopanResponse(

    @field:SerializedName("result")
    val result: String,

    @field:SerializedName("panel_specification")
    val panel_specification : PanelSpecification,

    @field:SerializedName("link1")
    val link1: String,

)

data class PanelSpecification(

    @field:SerializedName("solar_cell_type")
    val solarcelltype: String,

    @field:SerializedName("power_output")
    val poweroutput: String,

    @field:SerializedName("efficiency")
    val efficiency: String,

    @field:SerializedName("dimensions")
    val dimensions: String,

    @field:SerializedName("weight")
    val weight: String
)
