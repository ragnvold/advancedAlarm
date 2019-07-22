package com.westwin.advanced_alarm.Views.Fragments

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.westwin.advanced_alarm.Alarm.*
import com.westwin.advanced_alarm.R
import com.westwin.advanced_alarm.Alarm.Alarm
import com.westwin.advanced_alarm.Contracts.AlarmContract
import com.westwin.advanced_alarm.Notification.NotifyService
import com.westwin.advanced_alarm.Views.Activities.AlarmConstructorActivity
import com.westwin.advanced_alarm.Diff.RecyclerViewListener
import com.westwin.advanced_alarm.Presenters.AlarmPresenter

class AlarmFragment : Fragment(), AlarmContract.View {

    private var listener: OnFragmentInteractionListener? = null

    private var mPresenter: AlarmContract.Presenter = AlarmPresenter(this)

    private lateinit var alarmStorage: AlarmStorage

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mPresenter.onActivityCreated()
    }

    override fun activityCreated() {
        alarmReceiver = AlarmWentOffReceiver()
        LocalBroadcastManager.getInstance(activity!!)
            .registerReceiver(
                alarmReceiver,
                IntentFilter(NotifyService.ALARM_WENT_OFF_ACTION)
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.initialize(view)
        //visible nothing alarm
        //if (alarmAdapter.itemCount == 0)
    }

    override fun attachView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun addComponents() {
        alarmStorage = AlarmStorage(activity!!)
        alarmAdapter = AlarmAdapter(activity!!, alarmStorage.getAlarms())
        alarmUtil = AlarmUtil(activity!!)

        recyclerView.layoutManager = LinearLayoutManager(activity!!.baseContext)
    }

    override fun attachAdapter() {
        recyclerView.adapter = alarmAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(activity!!).unregisterReceiver(alarmReceiver)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object: RecyclerViewListener {
        @SuppressLint("StaticFieldLeak")
        private lateinit var recyclerView: RecyclerView
        @SuppressLint("StaticFieldLeak")
        private lateinit var alarmReceiver: BroadcastReceiver
        @SuppressLint("StaticFieldLeak")
        private lateinit var alarmAdapter: AlarmAdapter
        @SuppressLint("StaticFieldLeak")
        private lateinit var alarmUtil: AlarmUtil

        override fun itemPositionChecker(view: View): Int {
            return recyclerView.getChildAdapterPosition(view)
        }
    }

    class AlarmAddListenerImpl : AlarmConstructorActivity.AlarmAddListener {

        override fun onAlarmAdded(alarm: Alarm) {
            alarmAdapter.addAlarm(alarm)
            alarmUtil.scheduleAlarm(alarm)
        }
    }

    private class AlarmWentOffReceiver : BroadcastReceiver() {

        override fun onReceive(p0: Context?, p1: Intent?) {
            val alarm = AlarmUtil.readAlarm(p1!!.extras!!)
            alarmAdapter.deleteAlarm(alarm)
        }
    }
}
