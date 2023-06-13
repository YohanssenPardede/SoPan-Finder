package com.capstone.sopanfinder.view.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityResultBinding
import com.capstone.sopanfinder.view.ViewModelFactory
import com.capstone.sopanfinder.view.favorite.FavoriteActivity
import com.capstone.sopanfinder.view.favorite.FavoritePopup
import com.capstone.sopanfinder.view.profile.ProfileActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setBackgroundDrawable(getDrawable(R.drawable.ab_gradient))
        supportActionBar?.elevation = 0F
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = Html.fromHtml("<font color=\"transparent\">" + "" + "</font>")

        setResult()
    }

    private fun setResult(){
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val result = intent.getStringExtra(EXTRA_RESULT).toString()
        val name = intent.getStringExtra(EXTRA_NAME).toString()
        val cell = intent.getStringExtra(EXTRA_CELL).toString()
        val power = intent.getStringExtra(EXTRA_POWER).toString()
        val efficiency = intent.getStringExtra(EXTRA_EFFICIENCY).toString()
        val dimension = intent.getStringExtra(EXTRA_DIMENSION).toString()
        val weight = intent.getStringExtra(EXTRA_WEIGHT).toString()
        val link = intent.getStringExtra(EXTRA_LINK).toString()
        val linkImg = intent.getStringExtra(EXTRA_PHOTO).toString()

        binding.tvSopanName.text = name
        binding.tvCellType.text = "Solar Cell Type: $cell"
        binding.tvPowerOutput.text = "Power Output: $power"
        binding.tvEfficiency.text = "Efficiency: $efficiency"
        binding.tvDimensions.text = "Dimension: >$dimension"
        binding.tvWeight.text = "Weight: $weight"

        Glide.with(this).load(linkImg).into(binding.ivSopanPic);

        binding.commerceBtn.setOnClickListener{
            val uri: Uri = Uri.parse(link) // missing 'http://' will cause crashed
            val linkIntent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(linkIntent)
        }

        val resultViewModel = obtainViewModel(this@ResultActivity)
        setFavoriteFlag(resultViewModel, id, result, name, cell, power, efficiency, dimension, weight, link, linkImg)
    }

    private fun setFavoriteFlag(
        resultViewModel: ResultViewModel,
        id: Int,
        result: String,
        nameSopan: String,
        cellType: String,
        powerOutput: String,
        efficiency: String,
        weight: String, dimensions: String,
        link: String, linkImg: String
    ) {
        var flag = 0
        CoroutineScope(Dispatchers.IO).launch {
            val count = resultViewModel.checkSopan(nameSopan)

            withContext(Dispatchers.Main) {
                if (count == nameSopan) {
                    flag = 1
                    binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    flag = 0
                    binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_border_24)
                }
            }
        }

        binding.favoriteBtn.setOnClickListener {
            if (flag == 0) {
                resultViewModel.insertFavorite(id, result, nameSopan, cellType, powerOutput, efficiency, dimensions, weight, link, linkImg)
                binding.favoriteBtn.setImageResource(R.drawable.ic_baseline_favorite_24)
                flag = 1

                val intent = Intent(this@ResultActivity, FavoritePopup::class.java)
                intent.putExtra(EXTRA_POPUP, "Favorite successfully saved")
                startActivity(intent)
            } else if (flag == 1) {
                resultViewModel.removeFavorite(nameSopan)
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_border_24)
                flag = 0

                val intent = Intent(this@ResultActivity, FavoritePopup::class.java)
                intent.putExtra(EXTRA_POPUP, "Favorite has been removed")
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ResultViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ResultViewModel::class.java]
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

        const val EXTRA_ID = "extra_id"
        const val EXTRA_RESULT = "extra_result"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_CELL = "extra_cell"
        const val EXTRA_POWER = "extra_power"
        const val EXTRA_EFFICIENCY = "extra_efficiency"
        const val EXTRA_DIMENSION = "extra_dimension"
        const val EXTRA_WEIGHT = "extra_weight"
        const val EXTRA_LINK = "extra_link"
        const val EXTRA_PHOTO = "extra_photo"

        const val EXTRA_POPUP = "extra_popup"
    }
}