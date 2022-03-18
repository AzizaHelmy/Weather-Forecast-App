package com.example.weatherforecastapp.settings

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.navigation.Navigation
import androidx.preference.*
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.ThemeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(),SharedPreferences.OnSharedPreferenceChangeListener {

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
       // sharedPreferences =  preferenceScreen.sharedPreferences
        sharedPreferences = getDefaultSharedPreferences(requireContext())
        editor = sharedPreferences.edit()
        setThemePreference()
        setLanguage()
        setLocation()
        setNotification()
        setUnitPreference()
    }

    private fun setUnitPreference() {
        val unitPreference = findPreference<ListPreference>("unit")
        unitPreference?.setOnPreferenceClickListener {
            if (it.key == "unit") {

            }
            true
        }
    }

    private fun setNotification() {
        val notifictaio = findPreference<SwitchPreference>("notification_prefrence_key")
        notifictaio?.setOnPreferenceClickListener {
            if (it.key == "notification_prefrence_key") {
                //view?.findNavController()?.navigate(R.id.alertsFragment)
               // Navigation.findNavController(requireView()).navigate(R.id.alertsFragment)

            }
            true
        }
    }

    //======================================================
    private fun setLocation() {
        val locationUsingMap = findPreference<Preference>("CUSTOM_LOCATION")!!
        locationUsingMap.setOnPreferenceClickListener {
            val shared = PreferenceManager.getDefaultSharedPreferences(requireContext())
            if (shared.getBoolean("CUSTOM_LOCATION", true)) {
                Navigation.findNavController(requireView())
                    .navigate(R.id.action_settingsFragment_to_mapsFragment)
            }
            true
        }
    }

    //========================================================
    private fun setLanguage() {
        var language = findPreference<ListPreference>("language")
        language?.setOnPreferenceChangeListener { prefrs, obj ->
           changeLanguage=true
            val items = obj as String
            if (prefrs.key == "language") {
                when (items) {
                    "ar" -> setLocale("ar")
                    "en" -> setLocale("en")
                }
                ActivityCompat.recreate(requireActivity())
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
        config.locale = locale
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
//================================================
    override fun onPause() {
        if (changeLanguage){
            CoroutineScope(Dispatchers.Default).launch {
                restart()
            }
            changeLanguage=false
        }
        super.onPause()
    }
 //===============================================
    fun restart(){
        val intent = requireActivity().intent
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_NO_ANIMATION)
        requireActivity().overridePendingTransition(0, 0)
        requireActivity().finish()
        requireActivity().overridePendingTransition(0, 0)
        startActivity(intent)
    }
//================================================
    override fun onDestroy() {
        super.onDestroy()
    preferenceScreen.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)

    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {

    }
}
