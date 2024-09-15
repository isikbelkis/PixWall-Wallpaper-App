package com.example.backgroundapp.model

import com.google.gson.annotations.SerializedName

data class UserProfileImage(
    @SerializedName("small")
    val small: String?
)