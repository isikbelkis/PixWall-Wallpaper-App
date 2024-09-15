package com.example.backgroundapp.model

import com.google.gson.annotations.SerializedName

data class Sponsor(
    @SerializedName("id")
    val id: String?,
    @SerializedName("links")
    val links: Links?,
    @SerializedName("profile_image")
    val profileImage: SponsorProfileImage
)