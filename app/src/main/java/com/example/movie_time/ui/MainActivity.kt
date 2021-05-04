package com.example.movie_time.ui

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movie_time.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_movie, R.id.navigation_tv
            )
        )
        navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                R.id.navigation_movie -> {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                R.id.navigation_tv -> {
                    navView.visibility = View.VISIBLE
                    supportActionBar?.hide()
                }
                else -> {
                    navView.visibility = View.GONE
                    supportActionBar?.show()
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ||
                super.onSupportNavigateUp()
    }
}