package com.westwin.advanced_alarm.Alarm

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.westwin.advanced_alarm.Views.Activities.AlarmConstructorActivity
import com.westwin.advanced_alarm.Views.Fragments.AlarmFragment
import com.westwin.advanced_alarm.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AlarmAdapter(context: Context, alarms: Set<Alarm>) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>(),
    View.OnClickListener {

    private var mAlarmList: SortedList<Alarm>
    private var mAlarmStorage: AlarmStorage? = null
    private var mAlarmUtil: AlarmUtil? = null
    private var mContext: Context

    //constructors

    init {
        mAlarmList = SortedList(Alarm::class.java, SortedListCallback())
        mAlarmList.addAll(alarms)
        mContext = context
        mAlarmStorage = AlarmStorage(context)
        mAlarmUtil = AlarmUtil(context)
    }

    override fun getItemCount(): Int = mAlarmList.size()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtClk.text = "${mAlarmList[position].hour}:${getMinutes(mAlarmList[position].minute)}"
        holder.alarmName.text = mAlarmList[position].name

        holder.itemView.setOnClickListener(this)
    }

    private fun getMinutes(minutes: Int): String {
        if (minutes < 10)
            return "0$minutes"
        return minutes.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun onClick(p0: View?) {
        val intent = Intent(
            mContext.applicationContext,
            AlarmConstructorActivity::class.java
        )
        if (p0 != null)
            intent.putExtra(
                "alarm",
                mAlarmList[AlarmFragment.itemPositionChecker(p0)].toJSON()
            )
        mContext.startActivity(intent)
    }

    fun addAlarm(alarm: Alarm) {
        mAlarmList.add(alarm)
        notifyDataSetChanged()
    }

    fun deleteAlarm(alarm: Alarm) {
        mAlarmList.remove(alarm)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtClk: TextView = itemView.findViewById(R.id.txtClkRV)
        var alarmName: TextView = itemView.findViewById(R.id.alarm_nameRV)
    }

    class SortedListCallback : SortedList.Callback<Alarm>() {

        override fun areItemsTheSame(item1: Alarm?, item2: Alarm?): Boolean {
            return item1 == item2
        }

        override fun compare(o1: Alarm?, o2: Alarm?): Int {
            if (o1 != null && o2 != null)
                return o1.compareTo(o2)
            return 0
        }

        override fun areContentsTheSame(oldItem: Alarm?, newItem: Alarm?): Boolean {
            if (oldItem != null)
                return oldItem == newItem
            return false
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onChanged(position: Int, count: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onInserted(position: Int, count: Int) {}

        override fun onRemoved(position: Int, count: Int) {}
    }
}