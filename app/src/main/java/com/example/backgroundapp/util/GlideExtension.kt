package com.example.backgroundapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.backgroundapp.R

fun ImageView.loadImage(path: String) {
    val requestOptions = RequestOptions()
        .error(R.drawable.error)
        .centerCrop()
    Glide.with(this.context)
        .load(path)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadCircleImage(path: String) {
    val requestOptions = RequestOptions()
        .error(R.drawable.error)
        .circleCrop()
    Glide.with(this.context)
        .load(path)
        .apply(requestOptions)
        .into(this)
}

