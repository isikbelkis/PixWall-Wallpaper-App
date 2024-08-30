package com.example.backgroundapp.model

data class Category(
    val id: Int,
    val name: String,
    val imageResId: Int,
    var isSelected: Boolean = false)
