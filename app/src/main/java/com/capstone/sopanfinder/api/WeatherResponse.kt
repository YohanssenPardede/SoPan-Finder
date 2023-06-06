package com.capstone.sopanfinder.api

import com.google.gson.annotations.SerializedName

data class WeatherResponse(

//	@field:SerializedName("elevation")
//	val elevation: Double,
//
//	@field:SerializedName("hourly_units")
//	val hourlyUnits: HourlyUnits,
//
//	@field:SerializedName("generationtime_ms")
//	val generationtimeMs: Float,
//
//	@field:SerializedName("timezone_abbreviation")
//	val timezoneAbbreviation: String,
//
//	@field:SerializedName("timezone")
//	val timezone: String,

	@field:SerializedName("latitude")
	val latitude: Float,

//	@field:SerializedName("utc_offset_seconds")
//	val utcOffsetSeconds: Int,
//
	@field:SerializedName("hourly")
	val hourly: Hourly,

	@field:SerializedName("longitude")
	val longitude: Float
)

data class HourlyUnits(

	@field:SerializedName("temperature_2m")
	val temperature2m: String,

	@field:SerializedName("relativehumidity_2m")
	val relativehumidity2m: String,

	@field:SerializedName("windspeed_10m")
	val windspeed10m: String,

	@field:SerializedName("time")
	val time: String
)

data class Hourly(

	@field:SerializedName("temperature_2m")
	val temperature2m: List<Float>,

	@field:SerializedName("cloudcover")
	val cloudcover: List<Int>,

	@field:SerializedName("windspeed_10m")
	val windspeed10m : List<Float>,

	@field:SerializedName("rain")
	val rain : List<Float>,

	@field:SerializedName("precipitation_probability")
	val precipitationProbability : List<Int>,

	@field:SerializedName("time")
	val time : List<String>,

)
