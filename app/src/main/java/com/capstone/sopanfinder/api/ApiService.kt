package com.capstone.sopanfinder.api

import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("email") email: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword: String
    ): Call<Register>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Login>

    @GET("users")
    fun getUsers(
        @Query("token") token: String
    ): Call<Users>

    @GET("forecast")
    fun fetchWeather(
        @Query("latitude") latitude : Float,
        @Query("longitude") longitude : Float,
        @Query("hourly") hourly : String,
        @Query("forecast_days") forecast_days : Int
    ): Call<WeatherResponse>

    @FormUrlEncoded
    @POST("predict_text")
    fun fetchResult(
        @Field("date") date: List<String>,
        @Field("pv_demand") pv_demand: List<Int>,
        @Field("temp") temp: List<Double>,
        @Field("wind_speed") wind_speed: List<Double>,
        @Field("rain_1h") rain_1h: List<Double>,
        @Field("snow_1h") snow_1h: List<Double>,
        @Field("clouds_all") clouds_all: List<Int>,
        @Field("percip_1h") percip_1h: List<Double>
    ): Call<SopanResponse>
}