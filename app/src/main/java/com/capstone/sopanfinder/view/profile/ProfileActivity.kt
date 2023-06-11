package com.capstone.sopanfinder.view.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityProfileBinding
import com.capstone.sopanfinder.preference.UserPreference
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.login.LoginActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.profileName.text = UserPreference.getInstance(this).user.name
        binding.profileEmail.text = UserPreference.getInstance(this).user.email

        binding.btnLogout.setOnClickListener {
            UserPreference.getInstance(this).clearSession()
            Toast.makeText(this, resources.getString(R.string.success_logout), Toast.LENGTH_SHORT).show()
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
                finish()
            }
            R.id.favorite_menu -> {
                startActivity(Intent(this@ProfileActivity, FavoriteActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}