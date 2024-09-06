package com.example.backgroundapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.backgroundapp.databinding.ActivityBattomNavBinding
import com.example.backgroundapp.view.HomePageFragment
import com.example.backgroundapp.view.ProfileFragment
import com.example.backgroundapp.view.SearchFragment

class BattomNavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBattomNavBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBattomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomePageFragment())
                    true
                }

                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }

                R.id.nav_search -> {
                    loadFragment(SearchFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.containerFragment.id, fragment)
            .commit()
    }
}