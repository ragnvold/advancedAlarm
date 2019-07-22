package com.westwin.advanced_alarm.Notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.westwin.advanced_alarm.Alarm.Alarm
import com.westwin.advanced_alarm.Alarm.AlarmUtil

class NotifyReceiver : BroadcastReceiver() {

    override fun onReceive(p0: Context?, p1: Intent?) {
        if (
            p0 != null &&
            p1 != null
        ) {
            val alarm = Alarm().fromJSON(p1.getStringExtra("alarm")!!)
            AlarmUtil(p0).cancelAlarm(alarm)
            val manager = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(alarm.id)
        }
    }
}