package com.capstone.sopanfinder.view.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityResultBinding
import com.capstone.sopanfinder.view.home.HomeActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        binding.favoriteBtn.setOnClickListener {
            binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
        }


        binding.fab.setOnClickListener{
            startActivity(Intent(this@ResultActivity, HomeActivity::class.java))
        }
    }



}