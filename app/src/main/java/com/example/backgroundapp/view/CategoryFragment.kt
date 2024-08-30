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

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var adapter:CategoryAdapter
    private val selectedCategories = mutableListOf<Category>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(inflater, container, false)

        binding.buttonContinue.setOnClickListener {
            val action=CategoryFragmentDirections.actionCategoryFragmentToHomePageFragment()
            findNavController().navigate(action)
        }

        val categories= listOf(
            Category(1, "Animals", R.drawable.animals),
            Category(2,"Film",R.drawable.film),
            Category(3,"Nature",R.drawable.nature),
            Category(4,"Trawel",R.drawable.travel),
            Category(5,"People",R.drawable.people),
            Category(6,"Architecture",R.drawable.architecture_interiors),
            Category(7,"Food & Drink",R.drawable.food_drink),
            Category(8,"Textures Patterns",R.drawable.textures_patterns),
            Category(9,"Experimental",R.drawable.experimental),
            Category(10,"Photography",R.drawable.streeth_photography),
            Category(11,"Sports",R.drawable.sports),
            Category(12,"3D",R.drawable.dddd)
        )

        adapter= CategoryAdapter(categories){ category ->
            category.isSelected=!category.isSelected
            if (category.isSelected) {
                if (selectedCategories.size < 3) {
                    selectedCategories.add(category)
                } else {
                    category.isSelected = false
                }
            } else {
                selectedCategories.remove(category)
            }
            adapter.notifyDataSetChanged()
        }
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = this@CategoryFragment.adapter
        }
        return binding.root
    }
}