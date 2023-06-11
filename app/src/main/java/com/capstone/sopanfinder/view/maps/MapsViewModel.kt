package com.capstone.sopanfinder.view.maps

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.sopanfinder.api.ApiConfig
import com.capstone.sopanfinder.api.WeatherResponse
import com.capstone.sopanfinder.view.signup.SignupViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    fun getWeatherData(latitude: Float, longitude: Float){
        val client = ApiConfig.getWeatherApi().fetchWeather(latitude, longitude, "temperature_2m,cloudcover,windspeed_10m,rain,precipitation_probability", 1)
        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    Log.e(ContentValues.TAG, "onSuccess: ${response.message()}")
                    Log.d("latitude : ", responseBody.latitude.toString())
                    Log.d("longitude : ", responseBody.longitude.toString())
                    Log.d("cloudcover : ", responseBody.hourly.cloudcover.toString())
                    Log.d("windspeed_10m : ", responseBody.hourly.windspeed10m.toString())
                    Log.d("rain : ", responseBody.hourly.rain.toString())
                    Log.d("precipitation_probability : ", responseBody.hourly.precipitationProbability.toString())
                    Log.d("time ", responseBody.hourly.time.toString())

                }else{
                    Log.e(MapsViewModel.TAG, "Maps onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure2: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}