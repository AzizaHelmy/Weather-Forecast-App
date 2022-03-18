package com.example.weatherforecastapp

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class TestFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}