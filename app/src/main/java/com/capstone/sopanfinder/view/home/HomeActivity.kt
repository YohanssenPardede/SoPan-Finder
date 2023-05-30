package com.capstone.sopanfinder.view.home


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sopanfinder.view.profile.ProfileActivity
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.view.result.ResultActivity
import com.capstone.sopanfinder.databinding.ActivityHomeBinding
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import java.util.*
import kotlin.concurrent.schedule


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.searchBtn.setOnClickListener{
            val animation: Animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.blink)
                binding.searchBtn.startAnimation(animation)
                binding.homeStatus.setText(R.string.findsopan2)
                binding.homeDesc.setText(R.string.wait)

            Timer().schedule(2000) {
                binding.searchBtn.clearAnimation()
                startActivity(Intent(this@HomeActivity, ResultActivity::class.java))
            }
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