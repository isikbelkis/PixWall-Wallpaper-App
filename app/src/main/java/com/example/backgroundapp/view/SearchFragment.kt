package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.backgroundapp.R
import com.example.backgroundapp.adapter.PhotoAdapter
import com.example.backgroundapp.databinding.FragmentSearchBinding
import com.example.backgroundapp.viewmodel.SearchViewModel
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel:SearchViewModel by inject()
    private lateinit var adapter: PhotoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentSearchBinding.inflate(layoutInflater,container,false)
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}