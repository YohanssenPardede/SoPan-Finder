package com.capstone.sopanfinder.view.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.sopanfinder.api.ApiConfig
import com.capstone.sopanfinder.api.SopanResponse
import com.capstone.sopanfinder.api.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel : ViewModel() {
    private val _sopandata = MutableLiveData<SopanResponse>()
    val sopandata: LiveData<SopanResponse> = _sopandata

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    fun getWeatherData(latitude: Float, longitude: Float){
        val client = ApiConfig.getWeatherApi().fetchWeather(latitude, longitude, "temperature_2m,cloudcover,windspeed_10m,rain,precipitation_probability,snowfall", 1)
        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
//                val responseBody = response.body()

                if (response.isSuccessful) {
                    Log.i(TAG, "getWeatherData result: ${response.body()}")
                    _weatherData.value = response.body()

//                    val lat = responseBody.latitude
//                    val lon = responseBody.longitude

//                    val temp = responseBody.hourly.temperature2m
//                    val clouds_all = responseBody.hourly.cloudcover
//                    val pv_demand : List<Int> = listOf(62, 69, 73, 65, 60, 59, 92, 80, 75, 91, 100, 100, 98, 29, 100, 30, 38, 40, 17, 37, 44, 43, 45, 59)
//                    val wind_speed = responseBody.hourly.windspeed10m
//                    val rain_1h = responseBody.hourly.rain
//                    val snow_1h = responseBody.hourly.snowfall
//                    val percip_1h = responseBody.hourly.precipitationProbability
//                    val time = responseBody.hourly.time
//                    val date : List<String> = listOf("2023-06-11", "2023-06-11", "2023-06-11")

//                    fetchSopan(date, pv_demand, temp, wind_speed, rain_1h, snow_1h, clouds_all, percip_1h)

//                    Log.d("latitude : ", responseBody.latitude.toString())
//                    Log.d("temp : ", responseBody.hourly.temperature2m.toString())
//                    Log.d("pv demand", pv_demand.toString())
//                    Log.d("cloudcover : ", responseBody.hourly.cloudcover.toString())
//                    Log.d("windspeed_10m : ", responseBody.hourly.windspeed10m.toString())
//                    Log.d("rain : ", responseBody.hourly.rain.toString())
//                    Log.d("prec : ", responseBody.hourly.precipitationProbability.toString())
//                    Log.d("time ", responseBody.hourly.time.toString())
//                    Log.d("date ", date.toString())
//                    Log.d("snowfall ", responseBody.hourly.snowfall.toString())
//                    Log.d("latitude : ", responseBody.latitude.toString())

//                    Log.d("temp : ", temp.toString())
//                    Log.d("pv demand", pv_demand.toString())
//                    Log.d("cloudcover : ", clouds_all.toString())
//                    Log.d("windspeed_10m : ", wind_speed.toString())
//                    Log.d("rain : ", rain_1h.toString())
//                    Log.d("prec : ", percip_1h.toString())
//                    Log.d("time ", time.toString())
//                    Log.d("date ", date.toString())
//                    Log.d("snowfall ", snow_1h.toString())
                } else {
                    Log.e(TAG, "getWeatherData onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "getWeatherData onFailure: ${t.message.toString()}")
            }
        })
    }

    fun fetchSopan(date: List<String>, pv_demand: List<Int>, temp: List<Double>, wind_speed: List<Double>, rain_1h: List<Double>, snow_1h: List<Double>, clouds_all: List<Int>, percip_1h: List<Double>) {
        val client = ApiConfig.getSopanApi().fetchResult(date, pv_demand, temp, wind_speed, rain_1h, snow_1h, clouds_all, percip_1h)
        client.enqueue(object : Callback<SopanResponse> {
            override fun onResponse(call: Call<SopanResponse>, response: Response<SopanResponse>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    Log.i("Sopan Result:", responseBody!!.result)

                    _sopandata.value = response.body()
                } else {
                    Log.e(TAG, "fetchSopan onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SopanResponse>, t: Throwable) {
                Log.e(TAG, "fetchSopan onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}