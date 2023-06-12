package com.capstone.sopanfinder.view.maps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityMapsBinding
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.graph.GraphActivity
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.profile.ProfileActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel by viewModels<MapsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setTitle("SoPan Finder")

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

//        Toast.makeText(applicationContext, "Latitude : $lat Longitude: $lon", Toast.LENGTH_SHORT).show()
        Log.i("MapsActivity", "Latitude : $lat Longitude: $lon")

        val latitude = lat.toFloat()
        val longitude = lon.toFloat()

        //Get Weather Data From API, send to logcat and display graph data
        binding.confirmButton.setOnClickListener{
            mapsViewModel.getWeatherData(latitude, longitude)
            startActivity(Intent(this@MapsActivity, GraphActivity::class.java))
            finish()
        }

        binding.noButton.setOnClickListener{
            startActivity(Intent(this@MapsActivity, HomeActivity::class.java))
            finish()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                startActivity(Intent(this@MapsActivity, FavoriteActivity::class.java))
                finish()
            }
            R.id.profile_menu -> {
                startActivity(Intent(this@MapsActivity, ProfileActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}