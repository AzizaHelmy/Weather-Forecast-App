package com.example.weatherforecastapp

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.preference.PreferenceManager
import com.example.weatherforecastapp.databinding.ActivityMainBinding
import com.example.weatherforecastapp.utils.Constant
import com.example.weatherforecastapp.utils.InternetConnection
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadSettings()

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
    }

//====================================================
    private fun setLocale(lng: String) {
        val locale = Locale(lng)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources
            .updateConfiguration(config, baseContext.resources.displayMetrics)
    }

    private fun loadSettings()
    {
        val sp= PreferenceManager.getDefaultSharedPreferences(this)
        val languageKey=sp.getString("language","")
        setLocale(languageKey!!)
        Constant.appDefaultLanguage = languageKey
        Constant.api_lang=languageKey
    }
}