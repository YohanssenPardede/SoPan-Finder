package com.capstone.sopanfinder.view.favorite

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.database.FavoriteSopan
import com.capstone.sopanfinder.databinding.ActivityFavoriteBinding
import com.capstone.sopanfinder.view.ViewModelFactory
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.profile.ProfileActivity
import com.capstone.sopanfinder.view.result.ResultActivity

class FavoriteActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(_binding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavoriteSopan().observe(this) {
            if (it != null)
                adapter.setListFavorite(it)
        }

        adapter = FavoriteAdapter {
            favoriteViewModel.removeFavorite(it.nameSopan.toString())
        }

        setFavoriteData()
    }

    private fun setFavoriteData() {
        binding?.apply {
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvFavorite.setHasFixedSize(true)
            rvFavorite.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorites: FavoriteSopan) {
                Intent(this@FavoriteActivity, ResultActivity::class.java).also {
                    it.putExtra(ResultActivity.EXTRA_ID, favorites.id)
                    it.putExtra(ResultActivity.EXTRA_RESULT, favorites.result)
                    it.putExtra(ResultActivity.EXTRA_NAME, favorites.nameSopan)
                    it.putExtra(ResultActivity.EXTRA_CELL, favorites.cellType)
                    it.putExtra(ResultActivity.EXTRA_POWER, favorites.powerOutput)
                    it.putExtra(ResultActivity.EXTRA_EFFICIENCY, favorites.efficiency)
                    it.putExtra(ResultActivity.EXTRA_DIMENSION, favorites.dimensions)
                    it.putExtra(ResultActivity.EXTRA_WEIGHT, favorites.weight)
                    it.putExtra(ResultActivity.EXTRA_LINK, favorites.link)
                    it.putExtra(ResultActivity.EXTRA_PHOTO, favorites.linkImg)
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

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}