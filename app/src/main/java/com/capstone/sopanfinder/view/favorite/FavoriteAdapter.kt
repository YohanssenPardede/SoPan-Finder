package com.capstone.sopanfinder.view.favorite

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.database.FavoriteSopan
import com.capstone.sopanfinder.databinding.ItemFavoriteBinding
import com.capstone.sopanfinder.view.result.ResultActivity

class FavoriteAdapter(private val onDeleteClick: (FavoriteSopan) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    private val listFavorite = ArrayList<FavoriteSopan>()
    fun setListFavorite(listFav: List<FavoriteSopan>) {
        listFavorite.clear()
        listFavorite.addAll(listFav)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = listFavorite[position]
        holder.bind(favorite)
        holder.view.deleteIcon.setOnClickListener {
            onDeleteClick(favorite)
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    class ViewHolder(val view: ItemFavoriteBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(favorite: FavoriteSopan) {
            view.apply {
                Glide.with(itemView)
                    .load(favorite.linkImg)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(view.ivSopan)

                tvSopanName.text = favorite.nameSopan
                tvSopanLocation.text = getAddress(itemView.context, favorite.latitude, favorite.longitude)

                btnViewDetail.setOnClickListener {
                    val intent = Intent(itemView.context, ResultActivity::class.java).also {
                        it.putExtra(ResultActivity.EXTRA_ID, favorite.id)
                        it.putExtra(ResultActivity.EXTRA_RESULT, favorite.result)
                        it.putExtra(ResultActivity.EXTRA_NAME, favorite.nameSopan)
                        it.putExtra(ResultActivity.EXTRA_CELL, favorite.cellType)
                        it.putExtra(ResultActivity.EXTRA_POWER, favorite.powerOutput)
                        it.putExtra(ResultActivity.EXTRA_EFFICIENCY, favorite.efficiency)
                        it.putExtra(ResultActivity.EXTRA_DIMENSION, favorite.dimensions)
                        it.putExtra(ResultActivity.EXTRA_WEIGHT, favorite.weight)
                        it.putExtra(ResultActivity.EXTRA_LINK, favorite.link)
                        it.putExtra(ResultActivity.EXTRA_PHOTO, favorite.linkImg)
                    }

                    itemView.context.startActivity(intent)
                }
            }
        }

        private fun getAddress(context: Context, lat: Double, lon: Double): String {
            val geocoder = Geocoder(context)
            val location = geocoder.getFromLocation(lat, lon, 1)

            val fullAddress = if (location!!.size > 0) {
                val address = location[0]
                address.subAdminArea + ", " + address.adminArea
            } else {
                context.getString(R.string.null_location)
            }

            return fullAddress
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorites: FavoriteSopan)
    }
}