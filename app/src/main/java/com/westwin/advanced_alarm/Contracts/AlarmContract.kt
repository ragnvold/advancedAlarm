package com.westwin.advanced_alarm.Contracts

interface AlarmContract {

    interface View {
        fun activityCreated()
        fun attachView(view: android.view.View)
        fun addComponents()
        fun attachAdapter()
    }

    interface Presenter {
        fun onActivityCreated()
        fun initialize(view: android.view.View)
    }
}