package com.example.backgroundapp.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("profile_image")
    val profileImage: UserProfileImage
)