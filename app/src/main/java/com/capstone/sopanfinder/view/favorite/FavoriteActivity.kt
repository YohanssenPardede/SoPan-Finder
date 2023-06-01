package com.capstone.sopanfinder.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityFavoriteBinding
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.profile.ProfileActivity
import com.capstone.sopanfinder.view.result.ResultActivity

class FavoriteActivity : AppCompatActivity() {
    private val listFavorite = ArrayList<Favorites>()
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        listFavorite.addAll(getDummyData())
        setFavoriteData()
    }

    private fun getDummyData(): Collection<Favorites> {
        val name = resources.getStringArray(R.array.sopan_names)
        val location = resources.getStringArray(R.array.sopan_locations)
        val photo = resources.obtainTypedArray(R.array.sopan_photos)
        val listData = ArrayList<Favorites>()

        for (i in name.indices) {
            val group = Favorites(name[i], location[i], photo.getResourceId(i, -1))
            listData.add(group)
        }

        return listData
    }

    private fun setFavoriteData() {
        adapter = FavoriteAdapter(listFavorite)
        binding.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorites: Favorites) {
                Intent(this@FavoriteActivity, ResultActivity::class.java).also {
                    it.putExtra(EXTRA_NAME, favorites.name)
                    it.putExtra(EXTRA_LOCATION, favorites.location)
                    it.putExtra(EXTRA_PHOTO, favorites.photo)
                    startActivity(it)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                startActivity(Intent(this@FavoriteActivity, HomeActivity::class.java))
                finish()
            }
            R.id.profile_menu -> {
                startActivity(Intent(this@FavoriteActivity, ProfileActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_LOCATION = "extra_location"
        const val EXTRA_PHOTO = "extra_photo"
    }
}