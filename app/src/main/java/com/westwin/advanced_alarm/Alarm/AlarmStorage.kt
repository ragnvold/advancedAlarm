package com.westwin.advanced_alarm.Alarm

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.core.os.BuildCompat
import java.util.HashSet

class AlarmStorage(context: Context) {

    init {
        val storageContext: Context
        storageContext = if (BuildCompat.isAtLeastN()) {
            val deviceContext = context.createDeviceProtectedStorageContext()
            deviceContext
        } else {
            context
        }
        sharedPreferences = storageContext.getSharedPreferences(
            ALARM_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun saveAlarm(
        id: Int,
        hour: Int,
        minute: Int,
        name: String,
        volume: Int,
        vibration: Boolean,
        ringtone: String,
        isActive: Boolean
    ): Alarm {
        val alarm = Alarm(
            id,
            hour,
            minute,
            name,
            volume,
            vibration,
            ringtone,
            isActive
        )
        val editor = sharedPreferences.edit()
        editor.putString(
            alarm.id.toString(),
            alarm.toJSON()
        )
        editor.apply()
        return alarm
    }

    fun getAlarms(): Set<Alarm> {
        val alarms = HashSet<Alarm>()
        for (entry in sharedPreferences.all.entries) {
            alarms.add(Alarm().fromJSON(entry.value.toString()))
        }
        return alarms
    }

    fun deleteAlarm(deleted: Alarm) {
        for (entry in sharedPreferences.all.entries) {
            val alarm = Alarm().fromJSON(entry.value.toString())
            if (alarm.id == deleted.id) {
                val editor = sharedPreferences.edit()
                editor.remove(alarm.id.toString())
                editor.apply()
                return
            }
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var sharedPreferences: SharedPreferences
        private const val ALARM_PREFERENCES_NAME = "alarm_preferences"
    }
}