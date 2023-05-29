package com.capstone.sopanfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.sopanfinder.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteBtn.setOnClickListener {
            binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
        }

        binding.fab.setOnClickListener{
            startActivity(Intent(this@ResultActivity, HomeActivity::class.java))
        }
    }
}