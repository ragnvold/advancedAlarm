package com.westwin.advanced_alarm.Contracts

interface WatchContract {

    interface View {
        fun attachView(view: android.view.View)
        fun redrawWatch()
    }

    interface Presenter {
        fun initialize(view: android.view.View?)
        fun onResumeState()
    }
}