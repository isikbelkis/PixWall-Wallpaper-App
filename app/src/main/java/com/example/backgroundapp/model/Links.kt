package com.example.backgroundapp.model

import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("download")
    val download: String?
)