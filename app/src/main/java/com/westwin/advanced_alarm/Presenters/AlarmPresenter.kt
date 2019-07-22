package com.westwin.advanced_alarm.Presenters

import android.view.View
import com.westwin.advanced_alarm.Contracts.AlarmContract

class AlarmPresenter(private var mView: AlarmContract.View): AlarmContract.Presenter {

    override fun onActivityCreated() {
        mView.activityCreated()
    }

    override fun initialize(view: View) {
        mView.attachView(view)
        mView.addComponents()
        mView.attachAdapter()
    }
}