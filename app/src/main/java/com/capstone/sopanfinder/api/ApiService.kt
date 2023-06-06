package com.capstone.sopanfinder.api

import com.capstone.sopanfinder.api.FetchStory
import com.capstone.sopanfinder.api.Login
import com.capstone.sopanfinder.api.Register
import com.capstone.sopanfinder.api.UploadStory
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Register>

    @FormUrlEncoded
    @POST("login")
    fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<Login>

    @GET("stories")
    fun getStories(
        @Header("Authorization") token: String,
        @Query("size") size: Int
    ): Call<FetchStory>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): Call<UploadStory>

    @GET("forecast")
    fun fetchWeather(
        @Query("latitude") latitude : Float,
        @Query("longitude") longitude : Float,
        @Query("hourly") hourly : String,
        @Query("forecast_days") forecast_days : Int
    ): Call<WeatherResponse>


}