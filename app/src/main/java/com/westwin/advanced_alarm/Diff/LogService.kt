package com.westwin.advanced_alarm.Diff

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.IBinder
import android.util.Log

class LogService(context: Context) : Service() {

    private var mContext = context

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @SuppressLint("CommitPrefEdits")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        preferences = mContext.getSharedPreferences("logs.db", Context.MODE_PRIVATE).edit()
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private lateinit var preferences: SharedPreferences.Editor

        fun logger(key: String, value: String) {
            preferences.putString(key, value)
            preferences.apply()
        }
    }
}