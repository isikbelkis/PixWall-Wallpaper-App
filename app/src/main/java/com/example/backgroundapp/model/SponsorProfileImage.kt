package com.example.backgroundapp.model

import com.google.gson.annotations.SerializedName

data class SponsorProfileImage(
    @SerializedName("large")
    val large: String?,
    @SerializedName("medium")
    val medium: String?,
    @SerializedName("small")
    val small: String?
)