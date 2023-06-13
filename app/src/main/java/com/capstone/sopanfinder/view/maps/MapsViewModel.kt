package com.capstone.sopanfinder.view.maps

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.sopanfinder.api.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsViewModel(application: Application) : AndroidViewModel(application)  {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _sopandata = MutableLiveData<WeatherData>()
    val sopandata: LiveData<WeatherData> = _sopandata

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    fun getWeatherData(latitude: Float, longitude: Float){
        val client = ApiConfig.getWeatherApi().fetchWeather(latitude, longitude, "temperature_2m,cloudcover,windspeed_10m,rain,precipitation_probability,snowfall", 1)
        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val responseBody = response.body()

                if (responseBody != null) {
                    _weatherData.value = response.body()

                    val temp = responseBody.hourly.temperature2m
                    val clouds_all = responseBody.hourly.cloudcover
                    val pv_demand : List<Int> = listOf(62, 69, 73, 65, 60, 59, 92, 80, 75, 91, 100, 100, 98, 29, 100, 30, 38, 40, 17, 37, 44, 43, 45, 59)
                    val wind_speed = responseBody.hourly.windspeed10m
                    val rain_1h = responseBody.hourly.rain
                    val snow_1h = responseBody.hourly.snowfall
                    val percip_1h = responseBody.hourly.precipitationProbability
                    val time = responseBody.hourly.time
                    val result = "No Data Fetched"
                    val link = ""
                    val nameSopan = "No Data"
                    val linkImg = ""
                    val panelSpecification = PanelSpecification("No Data", "No Data", "No Data", "No Data", "No Data")


                    val w = WeatherData(
                        time, pv_demand as List<Float>, temp, wind_speed, rain_1h, snow_1h, clouds_all, percip_1h, result, link, nameSopan, panelSpecification, linkImg
                    )

                    fetchSopan(w)
                } else {
                    Log.e(TAG, "getWeatherData onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.e(TAG, "getWeatherData onFailure: ${t.message.toString()}")
            }
        })
    }

    fun fetchSopan(w : WeatherData) {
        val client = ApiConfig.getSopanApi().postData(w)
        client.enqueue(object : Callback<WeatherData> {
            override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                val responseBody = response.body()
                if (response.isSuccessful) {
                    Log.i("Sopan Result:", responseBody!!.result)

                    _sopandata.value = response.body()
                } else {
                    Log.e(TAG, "fetchSopan onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                }
            }

            override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                Log.e(TAG, "fetchSopan onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MapsViewModel"
    }
}