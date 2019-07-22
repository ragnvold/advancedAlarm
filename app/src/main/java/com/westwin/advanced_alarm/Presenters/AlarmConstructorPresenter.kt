package com.westwin.advanced_alarm.Presenters

import android.net.Uri
import com.westwin.advanced_alarm.Contracts.AlarmConstructorContract

class AlarmConstructorPresenter(private var mView: AlarmConstructorContract.View): AlarmConstructorContract.Presenter {

    override fun backBtnWasClicked() {
        mView.backBtn()
    }

    override fun prepareConstructor(extra: String) {
        mView.setAlarmConstructorData(extra)
    }

    override fun onRingtonePickerWasClicked() {
        mView.startRingtoneActivityResult()
    }

    override fun onAlarmAcceptWasClicked() {
        mView.alarmAccept()
    }

    override fun saveRingtone(ringtone: Uri) {

    }

    override fun initialize() {
        mView.attachView()
        mView.attachListener()
        mView.setToolbar()
    }

    override fun setSettings() {
        mView.attachSettings()
    }
}