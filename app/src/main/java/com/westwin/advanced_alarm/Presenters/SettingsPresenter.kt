package com.westwin.advanced_alarm.Presenters

import com.westwin.advanced_alarm.Contracts.SettingsContract

class SettingsPresenter(private var mView: SettingsContract.View): SettingsContract.Presenter {

    override fun initialize() {
        mView.attachViews()
        mView.attachFragment()
        mView.setToolbar()
    }

    override fun backBtnWasClicked() {
        mView.backBtn()
    }

    override fun setSettings() {
        mView.attachSettings()
    }
}