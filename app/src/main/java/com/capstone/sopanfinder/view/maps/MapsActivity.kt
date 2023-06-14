package com.capstone.sopanfinder.view.maps

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityMapsBinding
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.profile.ProfileActivity
import com.capstone.sopanfinder.view.result.ResultActivity
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

        supportActionBar?.title = "SoPan Finder"

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val lat = intent.getDoubleExtra("Latitude", 0.0)
        val lon = intent.getDoubleExtra("Longitude", 0.0)

        val location = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(location).title("You Are Here!"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

        Log.i("MapsActivity", "Latitude : $lat Longitude: $lon")

        val latitude = lat.toFloat()
        val longitude = lon.toFloat()

        // Get Weather Data From API, send to SoPan API and send intent to result activity
        binding.confirmButton.setOnClickListener{
            mapsViewModel.getWeatherData(latitude, longitude)

            mapsViewModel.sopanData.observe(this){
                val result = it.result
                val name = it.nameSopan
                val efficiency = it.panelSpecification.efficiency
                val cell = it.panelSpecification.solarCellType
                val power = it.panelSpecification.powerOutput
                val dimension = it.panelSpecification.dimensions
                val weight = it.panelSpecification.weight
                val link = it.link
                val linkImg = it.linkImg

                val intent = Intent(this@MapsActivity, ResultActivity::class.java)

                intent.putExtra(ResultActivity.EXTRA_RESULT, result)
                intent.putExtra(ResultActivity.EXTRA_NAME, name)
                intent.putExtra(ResultActivity.EXTRA_CELL, cell)
                intent.putExtra(ResultActivity.EXTRA_POWER, power)
                intent.putExtra(ResultActivity.EXTRA_EFFICIENCY, efficiency)
                intent.putExtra(ResultActivity.EXTRA_DIMENSION, dimension)
                intent.putExtra(ResultActivity.EXTRA_WEIGHT, weight)
                intent.putExtra(ResultActivity.EXTRA_LINK, link)
                intent.putExtra(ResultActivity.EXTRA_PHOTO, linkImg)

                startActivity(intent)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                finish()
            }
        }

        binding.noButton.setOnClickListener{
            startActivity(Intent(this@MapsActivity, HomeActivity::class.java))
            finish()
        }

        mapsViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mapsViewModel.error.observe(this) {
            if (it)
                Toast.makeText(this@MapsActivity, getString(R.string.error_fetch), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            binding.progressBar.visibility = View.GONE
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

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