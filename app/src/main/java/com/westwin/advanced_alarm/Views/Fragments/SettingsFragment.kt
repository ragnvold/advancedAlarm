package com.westwin.advanced_alarm.Views.Fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.westwin.advanced_alarm.Contracts.SettingsContract
import com.westwin.advanced_alarm.Presenters.SettingsFragmentPresenter
import com.westwin.advanced_alarm.R

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener, SettingsContract.FragmentView {

    private var mPresenter: SettingsContract.FragmentPresenter = SettingsFragmentPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPresenter.initializeOther()
    }

    override fun addSharedPreference() {
        settingsPreference = PreferenceManager.getDefaultSharedPreferences(activity!!.applicationContext)
        preference = Preference(activity!!.applicationContext)
    }

    override fun attachListener() {
        settingsPreference.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        mPresenter.initializePreference()
    }

    override fun attachPreference() {
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
    }

    override fun onResume() {
        super.onResume()
        settingsPreference.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        settingsPreference.unregisterOnSharedPreferenceChangeListener(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var settingsPreference: SharedPreferences
        @SuppressLint("StaticFieldLeak")
        private lateinit var preference: Preference
    }
}