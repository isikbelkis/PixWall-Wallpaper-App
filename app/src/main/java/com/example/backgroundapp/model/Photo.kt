package com.example.backgroundapp.model


import com.google.gson.annotations.SerializedName

data class PhotoItem(
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("urls")
    val urls: Urls?,
    @SerializedName("user")
    val user: User?,
)

