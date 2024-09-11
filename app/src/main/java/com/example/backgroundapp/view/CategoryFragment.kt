package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
            Category(
                id = 1,
                name = getString(R.string.animals),
                imageResId = R.drawable.animals,
                queryParam = "animals"
            ),
            Category(
                id = 2,
                name = getString(R.string.film),
                imageResId = R.drawable.film,
                queryParam = "film"
            ),
            Category(
                id = 3,
                name = getString(R.string.nature),
                imageResId = R.drawable.nature,
                queryParam = "nature"
            ),
            Category(
                id = 4,
                name = getString(R.string.travel),
                imageResId = R.drawable.travel,
                queryParam = "travel"
            ),
            Category(
                id = 5,
                name = getString(R.string.people),
                imageResId = R.drawable.people,
                queryParam = "people"
            ),
            Category(
                id = 6,
                name = getString(R.string.architecture),
                imageResId = R.drawable.architecture_interiors,
                queryParam = "architecture_interiors"
            ),
            Category(
                id = 7,
                name = getString(R.string.food_drink),
                imageResId = R.drawable.food_drink,
                queryParam = "food_drink"
            ),
            Category(
                id = 8,
                name = getString(R.string.textures_patterns),
                imageResId = R.drawable.textures_patterns,
                queryParam = "textures_patterns"
            ),
            Category(
                id = 9,
                name = getString(R.string.experimental),
                imageResId = R.drawable.experimental,
                queryParam = "experimental"
            ),
            Category(
                id = 10,
                name = getString(R.string.photography),
                imageResId = R.drawable.streeth_photography,
                queryParam = "streeth_photography"
            ),
            Category(
                id = 11,
                name = getString(R.string.sports),
                imageResId = R.drawable.sports,
                queryParam = "sports"
            ),
            Category(
                id = 12,
                name = getString(R.string.d),
                imageResId = R.drawable.dddd,
                queryParam = "3d"
            )
        )

        adapter = CategoryAdapter(categories) { category ->
            if (category.isSelected) {
                category.isSelected = false
                selectedCategories.remove(category)
            } else {
                if (selectedCategories.size < 3) {
                    category.isSelected = true
                    selectedCategories.add(category)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.categories3), Toast.LENGTH_SHORT).show()
                }
            }
            adapter.notifyItemChanged(categories.indexOf(category))
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = this@CategoryFragment.adapter
        }
        return binding.root
    }
}
