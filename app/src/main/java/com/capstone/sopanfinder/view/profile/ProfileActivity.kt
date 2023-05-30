package com.capstone.sopanfinder.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityProfileBinding
import com.capstone.sopanfinder.preference.UserPreference
import com.capstone.sopanfinder.view.login.LoginActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tempName.text = UserPreference.getInstance(this).user.name

        binding.btnLogout.setOnClickListener {
            UserPreference.getInstance(this).clearSession()
            Toast.makeText(this, resources.getString(R.string.success_logout), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}