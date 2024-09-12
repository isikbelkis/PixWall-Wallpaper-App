package com.example.backgroundapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.backgroundapp.view.OnboardingFragment
import com.example.backgroundapp.view.ViewPagerFirstFragment
import com.example.backgroundapp.view.ViewPagerSecondFragment
import com.example.backgroundapp.view.ViewPagerThirdFragment

class OnboardingAdapter(onboardingFragment: OnboardingFragment) : FragmentStateAdapter(onboardingFragment) {
    private val fragmentList = listOf(
        ViewPagerFirstFragment(),
        ViewPagerSecondFragment(),
        ViewPagerThirdFragment()
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}