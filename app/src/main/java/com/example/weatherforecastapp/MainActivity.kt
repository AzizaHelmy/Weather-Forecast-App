package com.example.weatherforecastapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.weatherforecastapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

/*               val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favFragment,
                R.id.alertsFragment,
                R.id.settingsFragment))

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNav.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)*/
        //binding.bottomNav.itemIconTintList = null
        binding.bottomNav.itemIconSize=53
        //binding.bottomNav.itemTextColor=R.color.white


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
        navController.addOnDestinationChangedListener( NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id==R.id.mapsFragment||destination.id==R.id.nextDaysFragment){
                binding.bottomNav.visibility = View.GONE
            }else{
                binding.bottomNav.visibility = View.VISIBLE
            }

        })

    }
}