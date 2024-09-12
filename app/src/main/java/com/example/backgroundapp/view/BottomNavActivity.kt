package com.example.backgroundapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.backgroundapp.R
import com.example.backgroundapp.databinding.ActivityBattomNavBinding
import com.example.backgroundapp.util.hide
import com.example.backgroundapp.util.show
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class BottomNavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBattomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBattomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: ChipNavigationBar = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)

        navView.setOnItemSelectedListener { itemId ->
            when (itemId) {
                R.id.nav_home -> navController.navigate(R.id.homePageFragment)
                R.id.nav_search -> navController.navigate(R.id.searchFragment)
                R.id.nav_profile -> navController.navigate(R.id.profileFragment)
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.detailFragment) {
                binding.navView.hide()
            } else {
                binding.navView.show()
            }
        }
    }
}