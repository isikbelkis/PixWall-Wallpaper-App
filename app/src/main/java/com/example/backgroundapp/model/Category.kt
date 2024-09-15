package com.example.backgroundapp.model

import com.example.backgroundapp.R


data class Category(
    val id: Int = 0,
    val name: String,
    val imageResId: Int,
    var isSelected: Boolean = false,
    val queryParam: String
)
object CategoryData {

    val categories = listOf(
        Category(
            id = 1,
            name = "Animals",
            imageResId = R.drawable.animals,
            queryParam = "animals"
        ),
        Category(
            id = 2,
            name = "Film",
            imageResId = R.drawable.film,
            queryParam = "film"
        ),
        Category(
            id = 3,
            name = "Nature",
            imageResId = R.drawable.nature,
            queryParam = "nature"
        ),
        Category(
            id = 4,
            name = "Travel",
            imageResId = R.drawable.travel,
            queryParam = "travel"
        ),
        Category(
            id = 5,
            name = "People",
            imageResId = R.drawable.people,
            queryParam = "people"
        ),
        Category(
            id = 6,
            name = "Architecture",
            imageResId = R.drawable.architecture_interiors,
            queryParam = "architecture_interiors"
        ),
        Category(
            id = 7,
            name = "Food & Drink",
            imageResId = R.drawable.food_drink,
            queryParam = "food_drink"
        ),
        Category(
            id = 8,
            name = "Textures & Patterns",
            imageResId = R.drawable.textures_patterns,
            queryParam = "textures_patterns"
        ),
        Category(
            id = 9,
            name = "Experimental",
            imageResId = R.drawable.experimental,
            queryParam = "experimental"
        ),
        Category(
            id = 10,
            name = "Photography",
            imageResId = R.drawable.streeth_photography,
            queryParam = "streeth_photography"
        ),
        Category(
            id = 11,
            name = "Sports",
            imageResId = R.drawable.sports,
            queryParam = "sports"
        ),
        Category(
            id = 12,
            name = "3D",
            imageResId = R.drawable.dddd,
            queryParam = "3d"
        )
    )
}
