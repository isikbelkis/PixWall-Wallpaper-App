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
) {
    data class Links(
        @SerializedName("download")
        val download: String?,
    ) {
        data class Sponsor(
            @SerializedName("id")
            val id: String?,
            @SerializedName("links")
            val links: Links?,
            @SerializedName("profile_image")
            val profileImage: ProfileImage?,
        ) {
            data class Links(
                @SerializedName("followers")
                val followers: String?,
                @SerializedName("following")
                val following: String?,
                @SerializedName("html")
                val html: String?,
                @SerializedName("likes")
                val likes: String?,
            )

            data class ProfileImage(
                @SerializedName("large")
                val large: String?,
                @SerializedName("medium")
                val medium: String?,
                @SerializedName("small")
                val small: String?
            )
        }
    }

    data class Urls(
        @SerializedName("full")
        val full: String?,
        @SerializedName("regular")
        val regular: String?,
    )

    data class User(
        @SerializedName("id")
        val id: String?,
        @SerializedName("last_name")
        val lastName: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("profile_image")
        val profileImage: ProfileImage?,
    ) {
        data class Links(
            @SerializedName("likes")
            val likes: String?,
            @SerializedName("photos")
            val photos: String?,
        )

        data class ProfileImage(
            @SerializedName("small")
            val small: String?
        )

    }
}

