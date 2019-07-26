package com.westwin.advanced_alarm.Contracts

interface SocialsContract {

    interface View {
        fun attachView(view: android.view.View)
        fun setConfig()
        fun attachAdapter()
        fun attachComponents()
    }

    interface Presenter {
        fun initialize(view: android.view.View)
    }

    interface Repository {
        fun login() {}
    }
}