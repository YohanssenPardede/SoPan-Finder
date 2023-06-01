package com.capstone.sopanfinder.view.graph

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.sopanfinder.databinding.ActivityGraphBinding
import com.capstone.sopanfinder.databinding.ActivityHomeBinding
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.result.ResultActivity

class GraphActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGraphBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGraphBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewSopan.setOnClickListener{
            startActivity(Intent(this@GraphActivity, ResultActivity::class.java))
        }

        binding.noButton.setOnClickListener{
            startActivity(Intent(this@GraphActivity, HomeActivity::class.java))
        }
    }
}