package com.capstone.sopanfinder.view.maps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityMapsBinding
import com.capstone.sopanfinder.view.graph.GraphActivity
import com.capstone.sopanfinder.view.home.HomeActivity
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var lat = intent.getDoubleExtra("Latitude", 0.0)
        var lon = intent.getDoubleExtra("Longitude", 0.0)

        // Add a marker in Sydney and move the camera
        val location = LatLng(lat, lon)
        mMap.addMarker(MarkerOptions().position(location).title("You Are Here!"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

        binding.confirmButton.setOnClickListener{
            startActivity(Intent(this@MapsActivity, GraphActivity::class.java))
        }

        binding.noButton.setOnClickListener{
            startActivity(Intent(this@MapsActivity, HomeActivity::class.java))
        }
    }
}