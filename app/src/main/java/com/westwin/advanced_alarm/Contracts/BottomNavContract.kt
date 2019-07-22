package com.westwin.advanced_alarm.Contracts

interface BottomNavContract {

    interface View {
        fun setFragmentTitle(fragment: Int)
        fun attachView()
        fun attachInflater()
        fun attachListener()
        fun setDefaultFragment()
        fun startSettings()
        fun startAlarmConstructor()
        fun attachSettings()
    }

    interface Presenter {
        fun initialize()
        fun setSettings()
        fun onBottomWatchFragmentWasClicked()
        fun onBottomAlarmsFragmentWasClicked()
        fun onSettingsOptionItemWasClicked()
        fun onAlarmConstructorOptionItemWasClicked()
    }
}