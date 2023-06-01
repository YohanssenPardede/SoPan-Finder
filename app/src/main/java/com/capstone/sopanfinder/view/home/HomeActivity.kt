package com.capstone.sopanfinder.view.home


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.capstone.sopanfinder.view.profile.ProfileActivity
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.view.result.ResultActivity
import com.capstone.sopanfinder.databinding.ActivityHomeBinding
import com.capstone.sopanfinder.preference.UserPreference
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.login.LoginActivity
import com.capstone.sopanfinder.view.maps.MapsActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.*
import kotlin.concurrent.schedule


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    lateinit var fusedLocationProviderClient : FusedLocationProviderClient

    var lat : Double = 0.0
    var lon : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)


        loginCheck()

        binding.searchBtn.setOnClickListener{
            getLocation()

            val animation: Animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
                binding.searchBtn.startAnimation(animation)
                binding.homeStatus.setText(R.string.findsopan2)
                binding.homeDesc.setText(R.string.wait)


            Timer().schedule(2000) {
                binding.searchBtn.clearAnimation()

                val intent = Intent(this@HomeActivity, MapsActivity::class.java)
                intent.putExtra("Latitude", lat)
                intent.putExtra("Longitude", lon)
                startActivity(intent)

//                startActivity(Intent(this@HomeActivity, ResultActivity::class.java))
            }
        }
    }

    private fun getLocation() {
        val task = fusedLocationProviderClient.lastLocation

        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if(it!= null){
                Toast.makeText(applicationContext, "${it.latitude} ${it.longitude}", Toast.LENGTH_SHORT).show()
                lat = it.latitude
                lon = it.longitude

            }
        }
    }


    private fun loginCheck() {
        if (UserPreference.getInstance(this).isLoggedIn) {
            supportActionBar?.title = StringBuilder("Hello, ").append(UserPreference.getInstance(this).user.name)
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onResume(){
        super.onResume()
        binding.homeStatus.setText(R.string.findsopan)
        binding.homeDesc.setText(R.string.home_desc_1)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> startActivity(Intent(this@HomeActivity, FavoriteActivity::class.java))
            R.id.profile_menu -> startActivity(Intent(this@HomeActivity, ProfileActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            finishAffinity()
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


}