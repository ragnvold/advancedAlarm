package com.westwin.advanced_alarm.Diff

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import androidx.preference.PreferenceManager
import com.westwin.advanced_alarm.R
import java.util.*

class PreferenceUtils {

    companion object Utils {

        private const val PREF_THEME = "theme"
        private const val PREF_LANGUAGE = "language"

        @SuppressLint("RestrictedApi")
        fun changeTheme(activity: Activity) {
            activity.setTheme(
                if (PreferenceManager(activity.applicationContext).sharedPreferences.getString(
                        PREF_THEME,
                        ""
                    ).equals("dark")
                ) R.style.Theme_MaterialComponents_NoActionBar else R.style.Theme_MaterialComponents_Light_NoActionBar
            )
        }

        fun defineLanguage(activity: Activity) {
            val locale = Locale(
                PreferenceManager.getDefaultSharedPreferences(activity.applicationContext).getString(
                    PREF_LANGUAGE, ""
                )!!
            )
            changeLanguage(activity, locale)
        }

        private fun changeLanguage(activity: Activity, locale: Locale) {
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            activity.baseContext.resources.updateConfiguration(
                config,
                activity.baseContext.resources.displayMetrics
            )
        }
    }
}