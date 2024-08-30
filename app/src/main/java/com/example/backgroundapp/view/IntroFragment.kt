package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.backgroundapp.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {
    private lateinit var binding: FragmentIntroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentIntroBinding.inflate(inflater, container, false)

        binding.buttonStart.setOnClickListener {
            val action=IntroFragmentDirections.actionIntroFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }
}