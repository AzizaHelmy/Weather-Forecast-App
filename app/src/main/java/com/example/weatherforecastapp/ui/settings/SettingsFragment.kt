package com.example.weatherforecastapp.ui.settings

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation
import androidx.preference.*
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.ThemeProvider
import java.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    var changeLanguage = false
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var prefrenceScreen: PreferenceScreen
    private lateinit var editor: SharedPreferences.Editor

    private val themeProvider by lazy { ThemeProvider(requireContext()) }
    private val themePreference by lazy {
        findPreference<ListPreference>(getString(R.string.theme_prefrence_key))
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        prefrenceScreen = preferenceScreen
        sharedPreferences = getDefaultSharedPreferences(requireContext())
        editor = sharedPreferences.edit()

        setThemePreference()
        setLanguage()
        setMapLocation()
        setNotification()
        setUnitPreference()
        setGPS()
    }

    private fun setGPS() {
        val location = findPreference<SwitchPreference>("USE_DEVICE_LOCATION")
        location?.setOnPreferenceClickListener {
            //get location to pass it to home???
            it.isEnabled
        }

    }

    private fun setUnitPreference() {
        val unitPreference = findPreference<ListPreference>("unit")
        unitPreference?.setOnPreferenceChangeListener { prefrs, obj ->
            val items = obj as String
            when (items) {
                "METRIC" -> {
                    editor.putString("unit", "C")
                    editor.commit()
                }
                "IMPERIAL" -> {
                    editor.putString("unit", "F")
                    editor.commit()
                }
                "STANDARD" -> {
                    editor.putString("unit", "K")
                    editor.commit()
                }

            }
            restartApp()
            true
        }
    }

    private fun setNotification() {
        val notifictaio = findPreference<SwitchPreference>("notification_prefrence_key")
        notifictaio?.setOnPreferenceClickListener {
            if (it.key == "notification_prefrence_key") {
                // Navigation.findNavController(requireView()).navigate(R.id.alertsFragment)
            }
            true
        }
    }

    //======================================================
    private fun setMapLocation() {
        val locationUsingMap = findPreference<Preference>("CUSTOM_LOCATION")!!
        locationUsingMap.setOnPreferenceClickListener {
            val shared = PreferenceManager.getDefaultSharedPreferences(requireContext())
            if (shared.getBoolean("CUSTOM_LOCATION", true)) {
                val bundle = Bundle()
                bundle.putInt("setting", 1)
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_settingsFragment_to_mapsFragment, bundle)
            }
            true
        }
    }

    //========================================================
    private fun setLanguage() {
        var language = findPreference<ListPreference>("language")
        language?.setOnPreferenceChangeListener { prefrs, obj ->
            changeLanguage = true
            val items = obj as String
            if (prefrs.key == "language") {
                when (items) {
                    "ar" -> setLocale("ar")
                    "en" -> setLocale("en")
                }

                restartApp()
            }
            editor.putBoolean("isUpdated", true)
            editor.commit()
            true
        }
    }

    private fun setLocale(lng: String) {
        val locale = Locale(lng)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    //=========================================================
    private fun setThemePreference() {
        themePreference?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                if (newValue is String) {
                    val theme = themeProvider.getTheme(newValue)
                    AppCompatDelegate.setDefaultNightMode(theme)
                }
                true
            }
        themePreference?.summaryProvider = getThemeSummaryProvider()
    }

    private fun getThemeSummaryProvider() =
        Preference.SummaryProvider<ListPreference> { preference ->
            themeProvider.getThemeDescriptionForPreference(preference.value)
        }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
    }

    private fun restartApp() {
        val intent = requireActivity().intent
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    or Intent.FLAG_ACTIVITY_NO_ANIMATION
        )
        requireActivity().overridePendingTransition(0, 0)
        requireActivity().finish()
        requireActivity().overridePendingTransition(0, 0)
        startActivity(intent)
    }


}
