package com.capstone.sopanfinder.view.result


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityResultBinding
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.favorite.FavoritePopup
import com.capstone.sopanfinder.view.maps.MapsActivity
import com.capstone.sopanfinder.view.maps.MapsViewModel
import com.capstone.sopanfinder.view.profile.ProfileActivity


class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private val mapsViewModel by viewModels<MapsViewModel>()
    private var flag : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.ab_gradient))
        supportActionBar?.setElevation(0F)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(Html.fromHtml("<font color=\"transparent\">" + "" + "</font>"));


        setFavoriteFlag()
        setResult()
    }

    private fun setResult(){
        val result = intent.getStringExtra("result")
        val name = intent.getStringExtra("name")
        val cell = intent.getStringExtra("cell")
        val power = intent.getStringExtra("power")
        val efficiency = intent.getStringExtra("efficiency")
        val dimension = intent.getStringExtra("dimension")
        val weight = intent.getStringExtra("weight")
        val link = intent.getStringExtra("link")
        val linkImg = intent.getStringExtra("linkImg")

        binding.tvSopanName.setText(name)
        binding.tvCellType.setText(cell)
        binding.tvPowerOutput.setText(power)
        binding.tvEfficiency.setText(efficiency)
        binding.tvDimensions.setText(dimension)
        binding.tvWeight.setText(weight)

        Glide.with(this).load(linkImg).into(binding.ivSopanPic);

        binding.commerceBtn.setOnClickListener{
            val uri: Uri = Uri.parse(link) // missing 'http://' will cause crashed
            val linkintent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(linkintent)
        }
    }

    private fun setFavoriteFlag() {
        binding.favoriteBtn.setOnClickListener {
            if (flag == 0) {
                binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                val intent = Intent(this@ResultActivity, FavoritePopup::class.java)
                intent.putExtra("popuptext", "Favorite successfully saved")
                flag = 1
                startActivity(intent)
            } else if (flag == 1) {
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_border_24)
                val intent = Intent(this@ResultActivity, FavoritePopup::class.java)
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

    override fun onResume(){
        super.onResume()
        setResult()
    }
    companion object {
        const val TAG = "ResultActivity"
    }
}