package com.example.backgroundapp.model

import com.google.gson.annotations.SerializedName

data class SponsorLinks(
    @SerializedName("followers")
    val followers: String?,
    @SerializedName("following")
    val following: String?,
    @SerializedName("html")
    val html: String?,
    @SerializedName("likes")
    val likes: String?
)