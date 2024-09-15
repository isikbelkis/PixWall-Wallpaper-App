package com.example.backgroundapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.backgroundapp.adapter.OnboardingAdapter
import com.example.backgroundapp.databinding.FragmentOnboardingBinding
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingFragment : Fragment() {
    private lateinit var binding: FragmentOnboardingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPager
        val tabDots = binding.tabDots
        val buttonLogin = binding.buttonLogin2
        val buttonSign = binding.buttonSign2

        val adapter = OnboardingAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabDots, viewPager) { tab, position ->
        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 2) {
                    buttonLogin.visibility = View.VISIBLE
                    buttonSign.visibility = View.VISIBLE
                } else {
                    buttonLogin.visibility = View.GONE
                    buttonSign.visibility = View.GONE
                }
            }
        })

        buttonLogin.setOnClickListener {
            val action=OnboardingFragmentDirections.actionOnboardingFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        buttonSign.setOnClickListener {
            val action=OnboardingFragmentDirections.actionOnboardingFragmentToSignupFragment()
            findNavController().navigate(action)
        }
    }
}
