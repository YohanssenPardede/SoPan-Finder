package com.capstone.sopanfinder.view.maps

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.sopanfinder.api.ApiConfig
import com.capstone.sopanfinder.api.Login
import com.capstone.sopanfinder.api.SopanResponse
import com.capstone.sopanfinder.api.WeatherResponse
import com.capstone.sopanfinder.view.result.ResultActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {

    private val _sopandata = MutableLiveData<SopanResponse>()
    val sopandata: LiveData<SopanResponse> = _sopandata

    fun getWeatherData(latitude: Float, longitude: Float){
        val client = ApiConfig.getWeatherApi().fetchWeather(latitude, longitude, "temperature_2m,cloudcover,windspeed_10m,rain,precipitation_probability,snowfall", 1)
        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val responseBody = response.body()

                if (responseBody != null) {
                    Log.e(ContentValues.TAG, "onSuccess: ${response.message()}")

                    val lat = responseBody.latitude
                    val lon = responseBody.longitude

                    val temp = responseBody.hourly.temperature2m
                    val clouds_all = responseBody.hourly.cloudcover
                    val pv_demand : List<Int> = listOf(62, 69, 73, 65, 60, 59, 92, 80, 75, 91, 100, 100, 98, 29, 100, 30, 38, 40, 17, 37, 44, 43, 45, 59)
                    val wind_speed = responseBody.hourly.windspeed10m
                    val rain_1h = responseBody.hourly.rain
                    val snow_1h = responseBody.hourly.snowfall
                    val percip_1h = responseBody.hourly.precipitationProbability
                    val time = responseBody.hourly.time
                    val date : List<String> = listOf("2023-06-11", "2023-06-11", "2023-06-11")


                    fetchSopan(date, pv_demand, temp, wind_speed, rain_1h, snow_1h, clouds_all, percip_1h)

                    Log.d("latitude : ", responseBody.latitude.toString())
                    Log.d("temp : ", responseBody.hourly.temperature2m.toString())
                    Log.d("pv demand", pv_demand.toString())
                    Log.d("cloudcover : ", responseBody.hourly.cloudcover.toString())
                    Log.d("windspeed_10m : ", responseBody.hourly.windspeed10m.toString())
                    Log.d("rain : ", responseBody.hourly.rain.toString())
                    Log.d("prec : ", responseBody.hourly.precipitationProbability.toString())
                    Log.d("time ", responseBody.hourly.time.toString())
                    Log.d("date ", date.toString())
                    Log.d("snowfall ", responseBody.hourly.snowfall.toString())

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


    fun fetchSopan(date:List<String>, pv_demand:List<Int>, temp:List<Float>, wind_speed:List<Float>, rain_1h:List<Float>, snow_1h:List<Float>, clouds_all:List<Int>, percip_1h:List<Float>) {
        val client = ApiConfig.getSopanApi().fetchResult(date, pv_demand, temp, wind_speed, rain_1h, snow_1h, clouds_all, percip_1h)
        client.enqueue(object : Callback<SopanResponse> {
            override fun onResponse(call: Call<SopanResponse>, response: Response<SopanResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    Log.i("Sopan Result:", responseBody!!.result)

                    _sopandata.value = response.body()
                } else {
                    Log.e(MapsViewModel.TAG, "sopan onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                }
            }
            override fun onFailure(call: Call<SopanResponse>, t: Throwable) {
                Log.e(MapsViewModel.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}