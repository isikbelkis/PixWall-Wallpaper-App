package com.example.backgroundapp.model

import com.google.gson.annotations.SerializedName

data class UserLinks(
    @SerializedName("likes")
    val likes: String?,
    @SerializedName("photos")
    val photos: String?
)