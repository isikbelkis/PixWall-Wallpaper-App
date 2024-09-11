package com.example.backgroundapp.view

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.backgroundapp.R
import com.example.backgroundapp.databinding.ActivityBattomNavBinding

class BattomNavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBattomNavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBattomNavBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> navController.navigate(R.id.hom)
                R.id.nav_search -> navController.navigate(R.id.searchFragment)
                R.id.nav_profile -> navController.navigate(R.id.profileFragment)
            }
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> hideBottomNav() // Belirli fragment'ta gizleme
                else -> showBottomNav() // Diğer fragment'larda gösterme
            }
        }


    }
    private fun hideBottomNav() {
        binding.navView.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE
    }
}