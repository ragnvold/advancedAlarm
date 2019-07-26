package com.westwin.advanced_alarm.Notification

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.westwin.advanced_alarm.Alarm.AlarmStorage
import com.westwin.advanced_alarm.Alarm.AlarmUtil
import com.westwin.advanced_alarm.Diff.App.Companion.CHANNEL_ID
import com.westwin.advanced_alarm.R

class NotifyService : IntentService("Alarm") {

    companion object {

        val ALARM_WENT_OFF_ACTION = NotifyService::class.java.name + ".ALARM_WENT_OFF"

        const val KEY_ALARM_ID = "alarm_id"
        const val KEY_ALARM_NAME = "alarm_month"
        const val KEY_ALARM_HOUR = "alarm_hour"
        const val KEY_ALARM_MINUTE = "alarm_minute"
        const val KEY_ALARM_VOLUME = "alarm_volume"
        const val KEY_ALARM_VIBRATION = "alarm_vibration"
        const val KEY_ALARM_RINGTONE = "alarm_ringtone"
        const val KEY_ALARM_ISACTIVE = "alarm_isActive"

        const val ALARM = "alarm"
    }

    override fun onHandleIntent(p0: Intent?) {
        val context = applicationContext
        val alarm = AlarmUtil
            .readAlarm(p0!!.extras!!)
        val notifyIntent = Intent(
            context,
            NotifyReceiver::class.java
        )
        notifyIntent.putExtra(
            ALARM,
            alarm.toJSON()
        )

        val notificationManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context
                .getSystemService(NotificationManager::class.java)
        } else {
            context.getSystemService(NotificationManager::class.java)
        }
        val builder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setCategory(Notification.CATEGORY_ALARM)
            .setChannelId(CHANNEL_ID)
            .setWhen(System.currentTimeMillis())
            .setShowWhen(true)
            .setContentTitle(
                getString(R.string.app_name)
            )
            .setAutoCancel(true)
            .setContentText(alarm.name)
            .addAction(
                0,
                getString(R.string.complete),
                PendingIntent.getBroadcast(
                    context,
                    0,
                    notifyIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

        if (alarm.vibration)
            builder.setDefaults(Notification.DEFAULT_VIBRATE)

        if (Uri.parse(alarm.ringtone) == Uri.EMPTY)
            builder.setDefaults(Notification.DEFAULT_SOUND)
        else
            builder.setSound(Uri.parse(alarm.ringtone))

        notificationManager
            .notify(
                alarm.id,
                builder.build()
            )

        val alarmStorage = AlarmStorage(context)
        alarmStorage.deleteAlarm(alarm)
        val wentOffIntent = Intent(ALARM_WENT_OFF_ACTION)
        wentOffIntent.putExtras(AlarmUtil.writeAlarm(alarm))
        LocalBroadcastManager.getInstance(context).sendBroadcast(wentOffIntent)
    }
}