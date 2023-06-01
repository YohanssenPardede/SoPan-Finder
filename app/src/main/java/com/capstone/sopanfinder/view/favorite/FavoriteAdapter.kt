package com.capstone.sopanfinder.view.favorite

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.capstone.sopanfinder.databinding.ItemFavoriteBinding
import com.capstone.sopanfinder.view.result.ResultActivity

class FavoriteAdapter(private val listFavorite: ArrayList<Favorites>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
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
        fun bind(favorite: Favorites) {
            view.apply {
                ivSopan.setImageResource(favorite.photo)
                tvSopanName.text = favorite.name
                tvSopanLocation.text = favorite.location
                btnViewDetail.setOnClickListener {
                    val intent = Intent(itemView.context, ResultActivity::class.java).also {
                        it.putExtra(FavoriteActivity.EXTRA_NAME, favorite.name)
                        it.putExtra(FavoriteActivity.EXTRA_LOCATION, favorite.location)
                        it.putExtra(FavoriteActivity.EXTRA_PHOTO, favorite.photo)
                    }

                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(favorites: Favorites)
    }
}