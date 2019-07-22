package com.westwin.advanced_alarm.Views.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.westwin.advanced_alarm.Alarm.Alarm
import com.westwin.advanced_alarm.Alarm.AlarmStorage
import com.westwin.advanced_alarm.Alarm.AlarmUtil
import com.westwin.advanced_alarm.Contracts.AlarmConstructorContract
import com.westwin.advanced_alarm.Views.Fragments.AlarmFragment
import com.westwin.advanced_alarm.R
import com.westwin.advanced_alarm.Diff.PreferenceUtils
import com.westwin.advanced_alarm.Presenters.AlarmConstructorPresenter
import java.security.SecureRandom
import java.util.*

class AlarmConstructorActivity : AppCompatActivity(), View.OnClickListener, AlarmConstructorContract.View {

    private var mPresenter: AlarmConstructorContract.Presenter = AlarmConstructorPresenter(this)

    private lateinit var alarmAddListener: AlarmAddListener
    private var alarmID: Int = 0
    private var alarmRingtone: Uri = Uri.EMPTY

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter.setSettings()
        super.onCreate(savedInstanceState)
        mPresenter.initialize()

        if (intent.getStringExtra(ALARM) != null) {
            CONSTRUCTOR_STATE = EDIT_ALARM_STATE
            mPresenter.prepareConstructor(intent.getStringExtra(ALARM)!!)
        } else
            CONSTRUCTOR_STATE = CREATE_ALARM_STATE
    }

    override fun attachView() {
        setContentView(R.layout.alarm_constructor)
        toolbar = findViewById(R.id.toolBar)
        timePicker = findViewById(R.id.timePicker)
        etName = findViewById(R.id.alarm_name)
        volumeSeekBar = findViewById(R.id.seekBar_volume)
        vibrationSwitch = findViewById(R.id.vibrationSwitch)
        ringtonePicker = findViewById(R.id.ringtone)
    }

    override fun attachListener() {
        alarmAddListener = AlarmFragment.AlarmAddListenerImpl()
        ringtonePicker.setOnClickListener(this)
    }

    override fun setToolbar() {
        toolbar.title = getString(R.string.create_new_alarm)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun attachSettings() {
        PreferenceUtils.changeTheme(this)
    }

    override fun setAlarmConstructorData(extra: String) {
        toolbar.title = getString(R.string.change_alarm)
        val alarm = Alarm().fromJSON(extra)
        alarmID = alarm.id
        timePicker.hour = alarm.hour
        timePicker.minute = alarm.minute
        etName.setText(alarm.name)
        volumeSeekBar.progress = alarm.volume
        vibrationSwitch.isChecked = alarm.vibration
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.ringtone -> {
                    mPresenter.onRingtonePickerWasClicked()
                }
            }
        }
    }

    override fun startRingtoneActivityResult() {
        startActivityForResult(Intent(RingtoneManager.ACTION_RINGTONE_PICKER), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK
            && data != null
            && data.data != null
        ) {
            val uri = data.data
            if (uri != Uri.EMPTY
                && uri != null
            ) {
                alarmRingtone = uri
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_alarm_toolbar, menu)
        if (menu != null)
            menu.findItem(R.id.alarm_accept).title = when (CONSTRUCTOR_STATE) {
                EDIT_ALARM_STATE -> {
                    getString(R.string.save)
                }
                else -> {
                    getString(R.string.create)
                }
            }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.alarm_accept -> {
            mPresenter.onAlarmAcceptWasClicked()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun alarmAccept() {
        val alarmTime = AlarmUtil(
            applicationContext
        ).getNextAlarmTime(
            timePicker.hour,
            timePicker.minute
        )
        val id = when (alarmID) {
            0 -> {
                SecureRandom().nextInt()
            }
            else -> {
                alarmID
            }
        }
        val alarmStorage = AlarmStorage(applicationContext).saveAlarm(
            id,
            alarmTime.get(Calendar.HOUR),
            alarmTime.get(Calendar.MINUTE),
            etName.text.toString(),
            volumeSeekBar.progress,
            vibrationSwitch.isChecked,
            alarmRingtone.toString(),
            true
        )

        alarmAddListener.onAlarmAdded(alarmStorage)
        onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        mPresenter.backBtnWasClicked()
        super.onBackPressed()
    }

    override fun backBtn() {
        startActivity(Intent(this, BottomNavActivity::class.java))
        finish()
    }

    private fun setTimeView() {
        when (PreferenceManager.getDefaultSharedPreferences(this).getString("language", "")) {
            "default" -> {
                timePicker.setIs24HourView(false)
            }
            "ru" -> {
                timePicker.setIs24HourView(true)
            }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var toolbar: Toolbar
        @SuppressLint("StaticFieldLeak")
        private lateinit var timePicker: TimePicker
        @SuppressLint("StaticFieldLeak")
        private lateinit var etName: EditText
        @SuppressLint("StaticFieldLeak")
        private lateinit var volumeSeekBar: SeekBar
        @SuppressLint("StaticFieldLeak")
        private lateinit var vibrationSwitch: Switch
        @SuppressLint("StaticFieldLeak")
        private lateinit var ringtonePicker: TextView

        private const val ALARM = "alarm"

        private const val CREATE_ALARM_STATE: String = "create"
        private const val EDIT_ALARM_STATE: String = "edit"

        private var CONSTRUCTOR_STATE: String = CREATE_ALARM_STATE
    }

    interface AlarmAddListener {
        fun onAlarmAdded(alarm: Alarm)
    }
}