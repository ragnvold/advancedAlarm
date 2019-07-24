package com.westwin.advanced_alarm.Presenters

import com.westwin.advanced_alarm.Contracts.BottomNavContract

class BottomNavPresenter(private var mView: BottomNavContract.View): BottomNavContract.Presenter {

    override fun onBottomAlarmsFragmentWasClicked() {
        mView.setFragmentTitle(1)
    }

    override fun onBottomWatchFragmentWasClicked() {
        mView.setFragmentTitle(0)
    }

    override fun onBottomSocialsFragmentWasClicked() {
        mView.setFragmentTitle(2)
    }

    override fun initialize() {
        mView.attachView()
        mView.attachInflater()
        mView.attachListener()
        mView.setDefaultFragment()
    }

    override fun onSettingsOptionItemWasClicked() {
        mView.startSettings()
    }

    override fun onAlarmConstructorOptionItemWasClicked() {
        mView.startAlarmConstructor()
    }

    override fun setSettings() {
        mView.attachSettings()
    }
}