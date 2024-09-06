package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.backgroundapp.R
import com.example.backgroundapp.adapter.CategoryAdapter
import com.example.backgroundapp.databinding.FragmentCategoryBinding
import com.example.backgroundapp.model.Category
import com.example.backgroundapp.viewmodel.CategoryViewModel
import org.koin.android.ext.android.inject

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapter: CategoryAdapter
    private val categoryViewModel: CategoryViewModel by inject()
    private val selectedCategories = mutableListOf<Category>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        binding.buttonContinue.setOnClickListener {
            selectedCategories.forEach { category ->
                categoryViewModel.addCategory(category)
            }
            val action = CategoryFragmentDirections.actionCategoryFragmentToBattomNavActivity()
            findNavController().navigate(action)
        }

        val categories = listOf(
            Category(id = 1, name = "Animals", imageResId = R.drawable.animals, queryParam = "animals"),
            Category(id = 2, name = "Film", imageResId = R.drawable.film, queryParam = "film"),
            Category(id = 3, name = "Nature", imageResId = R.drawable.nature, queryParam = "nature"),
            Category(id = 4, name = "Travel", imageResId = R.drawable.travel, queryParam = "travel"),
            Category(id = 5, name = "People", imageResId = R.drawable.people, queryParam = "people"),
            Category(id = 6, name = "Architecture", imageResId = R.drawable.architecture_interiors, queryParam = "architecture_interiors"),
            Category(id = 7, name = "Food & Drink", imageResId = R.drawable.food_drink, queryParam = "food_drink"),
            Category(id = 8, name = "Textures Patterns", imageResId = R.drawable.textures_patterns, queryParam = "textures_patterns"),
            Category(id = 9, name = "Experimental", imageResId = R.drawable.experimental, queryParam = "experimental"),
            Category(id = 10, name = "Photography", imageResId = R.drawable.streeth_photography, queryParam = "streeth_photography"),
            Category(id = 11, name = "Sports", imageResId = R.drawable.sports, queryParam = "sports"),
            Category(id = 12, name = "3D", imageResId = R.drawable.dddd, queryParam = "3d")
        )

        adapter = CategoryAdapter(categories) { category ->
            category.isSelected = !category.isSelected
            if (category.isSelected) {
                if (selectedCategories.size < 3) {
                    selectedCategories.add(category)
                } else {
                    category.isSelected = false
                }
            } else {
                selectedCategories.remove(category)
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = this@CategoryFragment.adapter
        }
        return binding.root
    }
}
