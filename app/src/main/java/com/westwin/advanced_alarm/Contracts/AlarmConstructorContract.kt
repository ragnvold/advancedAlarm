package com.westwin.advanced_alarm.Contracts

import android.net.Uri

interface AlarmConstructorContract {

    interface View {
        fun attachSettings()
        fun attachView()
        fun attachListener()
        fun setToolbar()
        fun setAlarmConstructorData(extra: String)
        fun startRingtoneActivityResult()
        fun alarmAccept()
        fun backBtn()
    }

    interface Presenter {
        fun initialize()
        fun setSettings()
        fun backBtnWasClicked()
        fun prepareConstructor(extra: String)
        fun onRingtonePickerWasClicked()
        fun saveRingtone(ringtone: Uri)
        fun onAlarmAcceptWasClicked()
    }
}