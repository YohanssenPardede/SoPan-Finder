package com.capstone.sopanfinder.view.maps

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.api.ApiConfig
import com.capstone.sopanfinder.api.Login
import com.capstone.sopanfinder.api.WeatherResponse
import com.capstone.sopanfinder.databinding.ActivityMapsBinding
import com.capstone.sopanfinder.preference.UserPreference
import com.capstone.sopanfinder.view.graph.GraphActivity
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.login.LoginViewModel
import com.capstone.sopanfinder.view.result.ResultActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lat = intent.getDoubleExtra("Latitude", 0.0)
        val lon = intent.getDoubleExtra("Longitude", 0.0)

        // Add a marker in Sydney and move the camera
        val location = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(location).title("You Are Here!"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))


        //Get Weather Data From API
        binding.confirmButton.setOnClickListener{


//            fun getWeatherData(lat: Float, lon: Float) {
//                _isLoading.value = true
            val client = ApiConfig.getWeatherApi().fetchWeather(lat.toFloat(), lon.toFloat())
            client.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                    val responseBody = response.body()

//                        _isLoading.value = false

                    if (responseBody != null) {
                        Log.e(ContentValues.TAG, "onSuccess: ${response.message()}")
                        Log.i("login token", responseBody.hourly.toString())
                    }else{
                        Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    }
                }
                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    Log.e(ContentValues.TAG, "onFailure2: ${t.message}")
                }
            })
//            }

            startActivity(Intent(this@MapsActivity, GraphActivity::class.java))
            finish()
        }

        binding.noButton.setOnClickListener{
            startActivity(Intent(this@MapsActivity, HomeActivity::class.java))
            finish()
        }
    }
}