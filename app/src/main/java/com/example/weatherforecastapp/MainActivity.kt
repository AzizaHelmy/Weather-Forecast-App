package com.example.weatherforecastapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.weatherforecastapp.databinding.ActivityMainBinding
import com.example.weatherforecastapp.utils.InternetConnection

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var internetConnection: InternetConnection
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.mapsFragment ||
                destination.id == R.id.nextDaysFragment ||
                destination.id == R.id.splashFragment ||
                destination.id == R.id.favNextDaysFragment ||
                destination.id == R.id.favWeatherFragment
            ) {
                binding.bottomNav.visibility = View.GONE
            } else {
                binding.bottomNav.visibility = View.VISIBLE
            }
        }

        internetConnection= InternetConnection(this)
        internetConnection.observe(this, androidx.lifecycle.Observer {
            if (!it)
            {
                binding.tvNoInternet.visibility= View.VISIBLE
                binding.bottomNav.visibility=View.GONE
                binding.tvNoInternet.isSelected=true
                binding.tvNoInternet.text=resources.getString(R.string.no_internet)
            }
            else
            {
                binding.tvNoInternet.visibility= View.GONE
               // binding.bottomNav.visibility=View.VISIBLE
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.alertsFragment)
        return navController.navigateUp() || navController.popBackStack() || super.onSupportNavigateUp()
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        navController.navigateUp();  //I tried this
//        navController.popBackStack()
//    }

}