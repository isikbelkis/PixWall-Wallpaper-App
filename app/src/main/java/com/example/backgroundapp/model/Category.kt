package com.example.backgroundapp.model


data class Category(
    val id: Int = 0,
    val name: String,
    val imageResId: Int,
    var isSelected: Boolean = false,
    val queryParam: String
)
