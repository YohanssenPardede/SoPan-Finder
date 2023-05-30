package com.capstone.sopanfinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.sopanfinder.databinding.ActivityMainBinding
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.login.LoginActivity
import com.capstone.sopanfinder.view.signup.SignupActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Button sementara untuk testing
        binding.homeButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }
        binding.loginButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
        binding.signupButton.setOnClickListener{
            startActivity(Intent(this@MainActivity, SignupActivity::class.java))
        }
    }
}