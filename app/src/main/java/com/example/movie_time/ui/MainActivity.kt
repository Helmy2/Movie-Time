package com.example.movie_time.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
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

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_movie, R.id.navigation_tv
            )
        )

        toolbar.setupWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> {
                    navView.visibility = View.VISIBLE
                    toolbar.visibility = View.GONE
                }
                R.id.navigation_movie -> {
                    navView.visibility = View.VISIBLE
                    toolbar.visibility = View.GONE
                }
                R.id.navigation_tv -> {
                    navView.visibility = View.VISIBLE
                    toolbar.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.GONE
                    toolbar.visibility = View.VISIBLE
                }
            }
        }




    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() ||
                super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO) {
//                try {
//                    Glide.get(applicationContext).clearDiskCache()
//                } catch (e: Exception) {
//                    Log.i("TAG", "1: ${e.toString()}")
//                }
//            }
//        }
//        lifecycleScope.launch {
//            try {
//                Glide.get(applicationContext).clearMemory()
//            } catch (e: Exception) {
//                Log.i("TAG", "2: ${e.toString()}")
//            }
//        }
    }
}



