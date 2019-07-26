package com.westwin.advanced_alarm.Presenters

import android.view.View
import com.westwin.advanced_alarm.Contracts.SocialsContract

class SocialsPresenter(private var mView: SocialsContract.View): SocialsContract.Presenter {

    override fun initialize(view: View) {
        mView.attachView(view)
        mView.attachComponents()
        mView.attachAdapter()
        mView.setConfig()
    }
}