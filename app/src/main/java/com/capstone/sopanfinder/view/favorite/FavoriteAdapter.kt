package com.capstone.sopanfinder.view.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.sopanfinder.database.FavoriteSopan
import com.capstone.sopanfinder.databinding.ItemFavoriteBinding
import com.capstone.sopanfinder.view.ViewModelFactory
import com.capstone.sopanfinder.view.result.ResultActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
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
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    class ViewHolder(private val view: ItemFavoriteBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(favorite: FavoriteSopan) {
            view.apply {
                Glide.with(itemView)
                    .load(favorite.linkImg)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(view.ivSopan)

                tvSopanName.text = favorite.nameSopan
                tvSopanLocation.text = "Location Dummy"

                btnViewDetail.setOnClickListener {
                    val intent = Intent(itemView.context, ResultActivity::class.java).also {
                        it.putExtra("id", favorite.id)
                        it.putExtra("result", favorite.result)
                        it.putExtra("name", favorite.nameSopan)
                        it.putExtra("cell", favorite.cellType)
                        it.putExtra("power", favorite.powerOutput)
                        it.putExtra("efficiency", favorite.efficiency)
                        it.putExtra("dimension", favorite.dimensions)
                        it.putExtra("weight", favorite.weight)
                        it.putExtra("link", favorite.link)
                        it.putExtra("linkImg", favorite.linkImg)
//                        it.putExtra(FavoriteActivity.EXTRA_LOCATION, favorite.location)
                    }

                    itemView.context.startActivity(intent)
                }

//                deleteIcon.setOnClickListener {
//                    val favoriteViewModel = obtainViewModel()
//                }
            }
        }

        private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorites: FavoriteSopan)
    }
}