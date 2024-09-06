package com.example.backgroundapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val imageResId: Int,
    var isSelected: Boolean = false,
    val queryParam: String
)
