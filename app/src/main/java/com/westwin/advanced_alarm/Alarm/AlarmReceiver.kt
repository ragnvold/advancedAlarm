package com.westwin.advanced_alarm.Alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.os.BuildCompat

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        val action: String = p1!!.action!!
        val alarmComplete = if (BuildCompat.isAtLeastN())
            Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(action)
        else
            Intent.ACTION_BOOT_COMPLETED.equals(action)
        if (!alarmComplete)
            return
        for (alarm in AlarmStorage(p0!!).getAlarms()) {
            AlarmUtil(p0).scheduleAlarm(alarm)
        }
    }
}