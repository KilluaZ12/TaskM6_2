package com.example.taskm6_2.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.taskm6_2.R
import com.example.taskm6_2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val topLevelDest = setOf(R.id.home_dest)

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val hostFragment = supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        navController = hostFragment.navController
        val configuration = AppBarConfiguration(topLevelDest)
        setSupportActionBar(binding.mainToolbar)
        setupActionBarWithNavController(navController, configuration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}