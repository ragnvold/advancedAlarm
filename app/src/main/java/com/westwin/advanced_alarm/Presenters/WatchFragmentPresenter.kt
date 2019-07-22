package com.westwin.advanced_alarm.Presenters

import android.view.View
import com.westwin.advanced_alarm.Contracts.WatchContract

class WatchFragmentPresenter(private var mView: WatchContract.View): WatchContract.Presenter {

    override fun initialize(view: View?) {
        if (view != null) {
            mView.attachView(view)
        }
    }

    override fun onResumeState() {
        mView.redrawWatch()
    }
}