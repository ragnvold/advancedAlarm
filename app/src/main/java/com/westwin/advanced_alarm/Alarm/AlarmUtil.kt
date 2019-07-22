package com.westwin.advanced_alarm.Alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.westwin.advanced_alarm.Notification.NotifyService
import java.util.*

class AlarmUtil(context: Context) {

    private lateinit var alarmManager: AlarmManager
    private var mContext: Context = context

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            this.alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun scheduleAlarm(alarm: Alarm) {
        val intent = Intent(
            mContext,
            NotifyService::class.java
        )
        val extras = writeAlarm(alarm)
        intent.putExtras(extras)

        val pendingIntent = PendingIntent
            .getService(
                mContext,
                alarm.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        val alarmTime = Calendar.getInstance()
        alarmTime.set(
            Calendar.MONTH,
            alarmTime.get(
                Calendar.MONTH
            )
        )
        alarmTime.set(
            Calendar.DAY_OF_MONTH,
            alarmTime.get(
                Calendar.DAY_OF_MONTH
            )
        )
        alarmTime.set(
            Calendar.HOUR,
            alarm.hour
        )
        alarmTime.set(
            Calendar.MINUTE,
            alarm.minute
        )
        alarmTime.set(
            Calendar.SECOND,
            0
        )

        val alarmClockInfo = AlarmManager.AlarmClockInfo(
            alarmTime.timeInMillis,
            pendingIntent
        )
        alarmManager.setAlarmClock(
            alarmClockInfo,
            pendingIntent
        )
    }

    fun cancelAlarm(alarm: Alarm) {
        val pendingIntent = PendingIntent.getService(
            this.mContext,
            alarm.id,
            Intent(
                this.mContext,
                NotifyService::class.java
            ),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }

    fun getNextAlarmTime(
        hour: Int,
        minute: Int
    ): Calendar {
        val alarmTime = Calendar.getInstance()
        alarmTime.set(
            Calendar.MONTH,
            alarmTime.get(Calendar.MONTH)
        )
        alarmTime.set(
            Calendar.DAY_OF_MONTH,
            alarmTime.get(Calendar.DAY_OF_MONTH)
        )
        alarmTime.set(
            Calendar.HOUR,
            hour
        )
        alarmTime.set(
            Calendar.MINUTE,
            minute
        )
        if ((alarmTime.timeInMillis - System.currentTimeMillis()) <= 0) {
            alarmTime.add(
                Calendar.DATE,
                1
            )
        }
        return alarmTime
    }

    companion object {

        fun readAlarm(extras: Bundle): Alarm {
            return Alarm(
                extras.getInt(NotifyService.KEY_ALARM_ID),
                extras.getInt(NotifyService.KEY_ALARM_HOUR),
                extras.getInt(NotifyService.KEY_ALARM_MINUTE),
                extras.getString(NotifyService.KEY_ALARM_NAME)!!,
                extras.getInt(NotifyService.KEY_ALARM_VOLUME),
                extras.getBoolean(NotifyService.KEY_ALARM_VIBRATION),
                extras.getString(NotifyService.KEY_ALARM_RINGTONE)!!,
                extras.getBoolean(NotifyService.KEY_ALARM_ISACTIVE)
            )
        }

        fun writeAlarm(alarm: Alarm): Bundle {
            val extras = Bundle()
            extras.putInt(NotifyService.KEY_ALARM_ID, alarm.id)
            extras.putInt(NotifyService.KEY_ALARM_HOUR, alarm.hour)
            extras.putInt(NotifyService.KEY_ALARM_MINUTE, alarm.minute)
            extras.putString(NotifyService.KEY_ALARM_NAME, alarm.name)
            extras.putInt(NotifyService.KEY_ALARM_VOLUME, alarm.volume)
            extras.putBoolean(NotifyService.KEY_ALARM_VIBRATION, alarm.vibration)
            extras.putString(NotifyService.KEY_ALARM_RINGTONE, alarm.ringtone)
            extras.putBoolean(NotifyService.KEY_ALARM_ISACTIVE, alarm.isActive)

            return extras
        }
    }
}