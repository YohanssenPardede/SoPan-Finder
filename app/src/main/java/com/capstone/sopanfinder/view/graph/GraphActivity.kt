package com.capstone.sopanfinder.view.graph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityGraphBinding
import com.capstone.sopanfinder.databinding.ActivityHomeBinding
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.profile.ProfileActivity
import com.capstone.sopanfinder.view.result.ResultActivity
import java.util.*
import kotlin.concurrent.schedule

class GraphActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            binding.graphAnim.pauseAnimation()
        }, 5000)

        binding.viewSopan.setOnClickListener{
            startActivity(Intent(this@GraphActivity, ResultActivity::class.java))
            finish()
        }

        binding.noButton.setOnClickListener{
            startActivity(Intent(this@GraphActivity, HomeActivity::class.java))
            finishAffinity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> startActivity(Intent(this@GraphActivity, FavoriteActivity::class.java))
            R.id.profile_menu -> startActivity(Intent(this@GraphActivity, ProfileActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}