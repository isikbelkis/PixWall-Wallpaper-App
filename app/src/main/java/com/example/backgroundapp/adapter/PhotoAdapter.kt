package com.example.backgroundapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.backgroundapp.R
import com.example.backgroundapp.databinding.WallpaperCardviewBinding
import com.example.backgroundapp.model.PhotoItem
import com.example.backgroundapp.util.loadCircleImage
import com.example.backgroundapp.util.loadImage

class PhotoAdapter(
    private var photos: List<PhotoItem>,
    private val onClick: (PhotoItem) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(private val binding: WallpaperCardviewBinding) :
        ViewHolder(binding.root) {
        fun bind(photoItem: PhotoItem) {
            with(binding) {
                val url = photoItem.urls?.full ?: photoItem.urls?.regular
                url?.let {
                    photoImageView.loadImage(it)
                }

                val profileImage =
                    photoItem.user?.profileImage?.small
                profileImage?.let {
                    profileImageView.loadCircleImage(it)
                }

                usernameTextView.text =
                    "${photoItem.user?.name ?: ""} ${photoItem.user?.lastName ?: ""}"

                itemView.setOnClickListener {
                    onClick(photoItem)
                }

                val fadeInAnimation = AnimationUtils.loadAnimation(itemView.context, R.anim.fade_in)
                itemView.startAnimation(fadeInAnimation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = WallpaperCardviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PhotoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    fun updatePhotos(newPhotos: List<PhotoItem>) {
        photos = newPhotos
        notifyDataSetChanged()
    }
}
