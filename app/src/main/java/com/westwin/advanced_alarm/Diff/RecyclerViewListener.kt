package com.westwin.advanced_alarm.Diff

import android.view.View

interface RecyclerViewListener {

    fun itemPositionChecker(view: View): Int

    fun updateRVData()
}