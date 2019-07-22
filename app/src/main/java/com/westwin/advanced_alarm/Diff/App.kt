package com.westwin.advanced_alarm.Diff

import android.app.Activity
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import com.westwin.advanced_alarm.Alarm.AlarmReceiver

class App : Application(), Application.ActivityLifecycleCallbacks {

    private var mActivity: Activity? = null

    override fun onCreate() {
        super.onCreate()
        registerReceiver(AlarmReceiver(), IntentFilter(Intent.ACTION_USER_PRESENT))
        createNotificationChannel()
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        mActivity = p0
    }

    override fun onActivityDestroyed(p0: Activity) {
        mActivity = null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "CHANNEL",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "This is CHANNEL"

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    override fun onActivityPaused(p0: Activity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStarted(p0: Activity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityStopped(p0: Activity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onActivityResumed(p0: Activity) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        const val CHANNEL_ID = "channel"
    }

}