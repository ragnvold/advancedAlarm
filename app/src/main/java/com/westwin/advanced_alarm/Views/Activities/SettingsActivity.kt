package com.westwin.advanced_alarm.Views.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.westwin.advanced_alarm.Contracts.SettingsContract
import com.westwin.advanced_alarm.Views.Fragments.SettingsFragment
import com.westwin.advanced_alarm.R
import com.westwin.advanced_alarm.Diff.PreferenceUtils
import com.westwin.advanced_alarm.Presenters.SettingsPresenter

class SettingsActivity : AppCompatActivity(), SettingsContract.View {

    private var mPresenter: SettingsContract.Presenter = SettingsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter.setSettings()
        super.onCreate(savedInstanceState)
        mPresenter.initialize()
    }

    override fun attachSettings() {
        PreferenceUtils.changeTheme(this)
    }

    override fun attachViews() {
        setContentView(R.layout.settings_activity)
        toolbar = findViewById(R.id.toolBar)
    }

    override fun attachFragment() {
        supportFragmentManager.beginTransaction().add(
            R.id.settings_container,
            SettingsFragment()
        ).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        mPresenter.backBtnWasClicked()
        super.onBackPressed()
    }

    override fun setToolbar() {
        toolbar.title = getString(R.string.settings)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun backBtn() {
        startActivity(Intent(this, BottomNavActivity::class.java))
        finish()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var toolbar: Toolbar
    }
}