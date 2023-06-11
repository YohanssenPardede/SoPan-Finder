package com.capstone.sopanfinder.view.result


import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityResultBinding
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.favorite.FavoritePopup
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.profile.ProfileActivity


class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var flag : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.ab_gradient))
        supportActionBar?.setElevation(0F)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(Html.fromHtml("<font color=\"transparent\">" + "" + "</font>"));


//        if (supportActionBar != null) {
//            supportActionBar!!.hide()
//        }



        binding.favoriteBtn.setOnClickListener {
            if(flag == 0) {
                binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                val intent = Intent(this, FavoritePopup::class.java)
                intent.putExtra("popuptext", "Favorite successfully saved")
                flag = 1
                startActivity(intent)
            }else if (flag == 1){
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_border_24)
                val intent = Intent(this, FavoritePopup::class.java)
                intent.putExtra("popuptext", "Favorite has been removed")
                flag = 0
                startActivity(intent)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> startActivity(Intent(this@ResultActivity, FavoriteActivity::class.java))
            R.id.profile_menu -> startActivity(Intent(this@ResultActivity, ProfileActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
        finish()
    }
}