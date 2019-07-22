package com.westwin.advanced_alarm.Contracts

interface SettingsContract {

    interface View {
        fun backBtn()
        fun setToolbar()
        fun attachViews()
        fun attachFragment()
        fun attachSettings()
    }

    interface Presenter {
        fun backBtnWasClicked()
        fun setSettings()
        fun initialize()
    }

    interface FragmentView {
        fun attachListener()
        fun addSharedPreference()
        fun attachPreference()
    }

    interface FragmentPresenter {
        fun initializePreference()
        fun initializeOther()
    }
}