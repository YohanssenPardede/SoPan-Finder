package com.capstone.sopanfinder.api

import com.google.gson.annotations.SerializedName

data class SopanResponse(
    @field:SerializedName("result")
    val result: String
)
