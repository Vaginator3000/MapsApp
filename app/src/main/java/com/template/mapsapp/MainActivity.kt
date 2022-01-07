package com.template.mapsapp

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapsapp.databinding.ActivityMainBinding
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {

 //   private lateinit var binding: ActivityMainBinding
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    //    binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration (
            setOf(
                R.id.navigation_map, R.id.navigation_places
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.login_fragment) {
                navView.visibility = View.GONE
            } else {
             //   toolbar.visibility = View.VISIBLE
                navView.visibility = View.VISIBLE
            }
        }


        MapKitFactory.setApiKey(getString(R.string.mapkit_api_key))
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        Log.e("AA", "${navController.currentDestination}")
        if (navController.currentDestination?.id == R.id.navigation_map ||
            navController.currentDestination?.id == R.id.navigation_places) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}