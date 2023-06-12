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

        setResult()
        setFavoriteFlag()

//        val name = intent.getStringExtra(FavoriteActivity.EXTRA_NAME)
//        val location = intent.getStringExtra(FavoriteActivity.EXTRA_LOCATION)
//        val photo = intent.getIntExtra(FavoriteActivity.EXTRA_PHOTO, 0)
    }

    private fun setResult(){
//        var temp: List<Double>
//        var clouds_all: List<Int>
        val pv_demand : List<Int> = listOf(62, 69, 73, 65, 60, 59, 92, 80, 75, 91, 100, 100, 98, 29, 100, 30, 38, 40, 17, 37, 44, 43, 45, 59)
//        var wind_speed: List<Double>
//        var rain_1h: List<Double>
//        var snow_1h: List<Double>
//        var precip_1h: List<Double>
        val date : List<String> = listOf("2023-06-11", "2023-06-11", "2023-06-11")

        mapsViewModel.weatherData.observe(this) {
            val temp = it.hourly.temperature2m
            val clouds_all = it.hourly.cloudcover
            val wind_speed = it.hourly.windspeed10m
            val rain_1h = it.hourly.rain
            val snow_1h = it.hourly.snowfall
            val precip_1h = it.hourly.precipitationProbability

            Log.i(TAG, "setResult temp $temp ||" +
                    " clouds_all $clouds_all ||" +
                    " pv_demand $pv_demand ||" +
                    " wind_speed $wind_speed ||" +
                    " rain $rain_1h ||" +
                    " snow $snow_1h ||" +
                    " precip $precip_1h ||" +
                    " date $date")
        }


        mapsViewModel.sopandata.observe(this) { sopandata ->
            val name = sopandata.nameSopan
            val cell = sopandata.panelSpecification.solarCellType
            val power = sopandata.panelSpecification.powerOutput
            val efficiency = sopandata.panelSpecification.efficiency
            val dimensions = sopandata.panelSpecification.dimensions
            val weight = sopandata.panelSpecification.weight
            val imagelink = sopandata.linkImg
            val link = sopandata.link

            binding.tvSopanName.setText(name)
            binding.tvCellType.setText(cell)
            binding.tvPowerOutput.setText(power)
            binding.tvEfficiency.setText(efficiency)
            binding.tvDimensions.setText(dimensions)
            binding.tvWeight.setText(weight)

            Glide.with(this).load(imagelink).into(binding.ivSopanPic);

            binding.commerceBtn.setOnClickListener{
                val uri: Uri = Uri.parse(link) // missing 'http://' will cause crashed
                val linkintent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(linkintent)
            }
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

    companion object {
        const val TAG = "ResultActivity"
    }
}