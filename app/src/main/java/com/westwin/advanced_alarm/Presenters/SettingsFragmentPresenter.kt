package com.westwin.advanced_alarm.Presenters

import com.westwin.advanced_alarm.Contracts.SettingsContract

class SettingsFragmentPresenter(private var mView: SettingsContract.FragmentView): SettingsContract.FragmentPresenter {

    override fun initializePreference() {
        mView.attachPreference()
    }

    override fun initializeOther() {
        mView.addSharedPreference()
        mView.attachListener()
    }
}