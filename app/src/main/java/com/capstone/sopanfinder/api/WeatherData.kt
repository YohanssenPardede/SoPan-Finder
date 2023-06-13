package com.capstone.sopanfinder.api

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @field:SerializedName("date")
    val date: List<String>,

    @field:SerializedName("pv_demand")
    val pv_demand: List<Float>,

    @field:SerializedName("temp")
    val temp: List<Float>,

    @field:SerializedName("wind_speed")
    val wind_speed: List<Float>,

    @field:SerializedName("rain_1h")
    val rain_1h: List<Float>,

    @field:SerializedName("snow_1h")
    val snow_1h: List<Float>,

    @field:SerializedName("clouds_all")
    val clouds_all: List<Float>,

    @field:SerializedName("percip_1h")
    val percip_1h: List<Float>,

    @field:SerializedName("result")
    val result: String,

    @field:SerializedName("link")
    val link: String,

    @field:SerializedName("name_sopan")
    val nameSopan: String,

    @field:SerializedName("panel_specification")
    val panelSpecification: PanelSpecification,

    @field:SerializedName("link_img")
    val linkImg: String
)

data class PanelSpecification(

    @field:SerializedName("efficiency")
    val efficiency: String,

    @field:SerializedName("power_output")
    val powerOutput: String,

    @field:SerializedName("weight")
    val weight: String,

    @field:SerializedName("solar_cell_type")
    val solarCellType: String,

    @field:SerializedName("dimensions")
    val dimensions: String
)