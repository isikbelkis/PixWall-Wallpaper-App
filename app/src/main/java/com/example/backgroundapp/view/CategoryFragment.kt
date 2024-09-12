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
import com.example.backgroundapp.model.CategoryData
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

        val categories = CategoryData.categories

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
