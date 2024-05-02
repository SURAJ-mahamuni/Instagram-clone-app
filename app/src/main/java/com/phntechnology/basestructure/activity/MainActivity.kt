package com.phntechnology.basestructure.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.commonutils.util.hideView
import com.example.commonutils.util.showView
import com.phntechnology.basestructure.R
import com.phntechnology.basestructure.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeNavController()

        fragmentDestinationHandle()

        initializeListener()

    }

    private fun initializeListener() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    if (binding.bottomNavigation.menu.findItem(binding.bottomNavigation.selectedItemId) != item) {
                        navController.navigate(R.id.homeFragment)
                    }
                    true
                }
                R.id.menu_profile -> {
                    if (binding.bottomNavigation.menu.findItem(binding.bottomNavigation.selectedItemId) != item) {
                        navController.navigate(R.id.profileFragment)
                    }
                    true
                }
                R.id.menu_reel -> {
                    if (binding.bottomNavigation.menu.findItem(binding.bottomNavigation.selectedItemId) != item) {
                        navController.navigate(R.id.reelFragment)
                    }
                    true
                }

                else -> {
                    false
                }
            }
        }
    }

    private fun initializeNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        navController = navHostFragment.navController
    }

    private fun fragmentDestinationHandle() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashScreenFragment -> {
                    binding.bottomNavigation.hideView()
                }

                R.id.homeFragment -> {
                    binding.bottomNavigation.showView()
                }
                R.id.profileFragment -> {
                    binding.bottomNavigation.showView()
                }
                R.id.reelFragment -> {
                    binding.bottomNavigation.showView()
                }

                else -> {}
            }
        }
    }
}