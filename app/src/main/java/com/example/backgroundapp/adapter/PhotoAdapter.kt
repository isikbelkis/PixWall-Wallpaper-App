package com.example.backgroundapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.backgroundapp.R
import com.example.backgroundapp.model.PhotoItem

class PhotoAdapter(
    private var photos: List<PhotoItem>,
    private val onClick: (PhotoItem) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView3)

        fun bind(photoItem: PhotoItem) {
            val url = photoItem.urls?.regular ?: photoItem.urls?.regular
            Glide.with(itemView.context)
                .load(url)
                .into(imageView)

            itemView.setOnClickListener {
                onClick(photoItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.wallpaper_cardview, parent, false)
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    fun updatePhotos(newPhotos: List<PhotoItem>) {
        photos = newPhotos
    }
}
