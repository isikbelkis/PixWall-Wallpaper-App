package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.backgroundapp.adapter.PhotoAdapter
import com.example.backgroundapp.databinding.FragmentSearchBinding
import com.example.backgroundapp.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by inject()
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.searchRecycler.layoutManager = layoutManager

        adapter = PhotoAdapter(emptyList()) { photoItem ->
            val action =
                SearchFragmentDirections.actionSearchFragment2ToDetailFragment2(id = photoItem.id!!)
            findNavController().navigate(action)
        }

        binding.searchRecycler.adapter = adapter

        searchViewModel.searchResultsLiveData.observe(viewLifecycleOwner) { photos ->
            adapter.updatePhotos(photos)
        }

        searchViewModel.isLoading.observe(viewLifecycleOwner) { loading ->
            binding.progressBar.isVisible = loading
        }

        searchViewModel.searchQueryLiveData.observe(viewLifecycleOwner) { query ->
            binding.search.setQuery(query, false)
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchViewModel.searchPhotos(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
