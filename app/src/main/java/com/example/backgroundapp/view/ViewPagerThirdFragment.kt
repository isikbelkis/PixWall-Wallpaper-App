package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.backgroundapp.databinding.FragmentViewPagerThirdBinding

class ViewPagerThirdFragment : Fragment() {
    private lateinit var binding: FragmentViewPagerThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewPagerThirdBinding.inflate(inflater, container, false)
        return binding.root
    }
}

