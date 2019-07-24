package com.westwin.advanced_alarm.Views.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.westwin.advanced_alarm.Contracts.BottomNavContract
import com.westwin.advanced_alarm.Views.Fragments.AlarmFragment
import com.westwin.advanced_alarm.Views.Fragments.WatchFragment
import com.westwin.advanced_alarm.R
import com.westwin.advanced_alarm.Diff.PreferenceUtils
import com.westwin.advanced_alarm.Presenters.BottomNavPresenter
import com.westwin.advanced_alarm.Views.Fragments.SocialsFragment

class BottomNavActivity : AppCompatActivity(), WatchFragment.OnFragmentInteractionListener,
    AlarmFragment.OnFragmentInteractionListener, SocialsFragment.OnFragmentInteractionListener, Toolbar.OnMenuItemClickListener, BottomNavContract.View {

    private var mPresenter: BottomNavContract.Presenter = BottomNavPresenter(this)

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_watch -> {
                loadFragment(WatchFragment())
                mPresenter.onBottomWatchFragmentWasClicked()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_alarms -> {
                loadFragment(AlarmFragment())
                mPresenter.onBottomAlarmsFragmentWasClicked()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_socials -> {
                loadFragment(SocialsFragment())
                mPresenter.onBottomSocialsFragmentWasClicked()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
            return true
        }
        return false
    }

    override fun setFragmentTitle(fragment: Int) {
        toolbar.title = when (fragment) {
            0 -> {
                getString(R.string.watch)
            }
            1 -> {
                getString(R.string.alarm_clocks)
            }
            else -> {
                getString(R.string.socials)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter.setSettings()
        super.onCreate(savedInstanceState)
        mPresenter.initialize()
    }

    override fun attachSettings() {
        PreferenceUtils.changeTheme(this)
        PreferenceUtils.defineLanguage(this)
    }

    override fun attachView() {
        setContentView(R.layout.bottom_nav_activity)
        toolbar = findViewById(R.id.toolBar)
        navView = findViewById(R.id.nav_view)
    }

    override fun attachInflater() {
        toolbar.inflateMenu(R.menu.settings_menu)
    }

    override fun attachListener() {
        toolbar.setOnMenuItemClickListener(this)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun setDefaultFragment() {
        navView.selectedItemId = R.id.navigation_alarms
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item != null)
            return when (item.itemId) {
                R.id.settings_menu
                -> {
                    mPresenter.onSettingsOptionItemWasClicked()
                    true
                }
                R.id.create_new_alarm
                -> {
                    mPresenter.onAlarmConstructorOptionItemWasClicked()
                    true
                }
                else
                -> {
                    super.onOptionsItemSelected(item)
                }
            }
        return false
    }

    override fun startSettings() {
        startActivity(
            Intent(
                this@BottomNavActivity,
                SettingsActivity::class.java
            )
        )
        finish()
    }

    override fun startAlarmConstructor() {
        startActivity(
            Intent(
                this@BottomNavActivity,
                AlarmConstructorActivity::class.java
            )
        )
        finish()
    }

    override fun onFragmentInteraction(uri: Uri) {}

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var navView: BottomNavigationView
        @SuppressLint("StaticFieldLeak")
        private lateinit var toolbar: Toolbar
    }
}
