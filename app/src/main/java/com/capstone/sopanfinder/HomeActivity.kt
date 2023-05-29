package com.capstone.sopanfinder


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sopanfinder.databinding.ActivityHomeBinding
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


}