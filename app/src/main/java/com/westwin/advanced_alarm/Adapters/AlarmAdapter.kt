package com.westwin.advanced_alarm.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.requests.VKRequest
import com.westwin.advanced_alarm.Models.Alarm
import com.westwin.advanced_alarm.Alarm.AlarmStorage
import com.westwin.advanced_alarm.Alarm.AlarmUtil
import com.westwin.advanced_alarm.Diff.VKUserRequest
import com.westwin.advanced_alarm.R
import com.westwin.advanced_alarm.Views.Activities.AlarmConstructorActivity
import org.json.JSONObject

class AlarmAdapter(context: Context, alarms: Set<Alarm>) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    private var mAlarmList: SortedList<Alarm>
    private var mAlarmStorage: AlarmStorage? = null
    private var mAlarmUtil: AlarmUtil? = null
    private var mContext: Context

    //constructors

    init {
        mAlarmList = SortedList(
            Alarm::class.java,
            SortedListCallback()
        )
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
        holder.alarmActive.isChecked = mAlarmList[position].isActive
        holder.alarmShare.text = "Share"

        holder.itemView.setOnClickListener {
            val intent = Intent(
                mContext.applicationContext,
                AlarmConstructorActivity::class.java
            )
            intent.putExtra(
                "alarm",
                mAlarmList[position].toJSON()
            )
            mContext.startActivity(intent)
        }
        holder.alarmShare.setOnClickListener{
            VK.execute(VKRequest<JSONObject>("wall.post").addParam("owner_id", 106094781).addParam("friends_only", 1).addParam("message", "YES"), object : VKApiCallback<JSONObject> {
                override fun success(result: JSONObject) {
                    Log.i("TAG", "success")
                }

                override fun fail(error: VKApiExecutionException) {
                    Log.i("TAG", error.localizedMessage)
                }
            })
        }
        holder.alarmActive.setOnClickListener {
            when (mAlarmList[position].isActive) {
                true -> {
                    deactivateAlarm(mAlarmList[position])
                }
                false -> {
                    activateAlarm(mAlarmList[position])
                }
            }
            mAlarmList[position].isActive =
                !mAlarmList[position].isActive
        }
    }

    private fun getMinutes(minutes: Int): String {
        if (minutes < 10)
            return "0$minutes"
        return minutes.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.alarms_item,
                parent,
                false
            )
        )
    }

    fun addAlarm(alarm: Alarm) {
        mAlarmList.add(alarm)
        notifyDataSetChanged()
    }

    fun deactivateAlarm(alarm: Alarm) {
        mAlarmStorage!!.saveAlarm(
            alarm.id,
            alarm.hour,
            alarm.minute,
            alarm.name,
            alarm.volume,
            alarm.vibration,
            alarm.ringtone,
            false
        )
        mAlarmUtil!!.cancelAlarm(alarm)
        notifyDataSetChanged()
    }

    private fun activateAlarm(alarm: Alarm) {
        mAlarmStorage!!.saveAlarm(
            alarm.id,
            alarm.hour,
            alarm.minute,
            alarm.name,
            alarm.volume,
            alarm.vibration,
            alarm.ringtone,
            true
        )
        mAlarmUtil!!.scheduleAlarm(alarm)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtClk: TextView = itemView.findViewById(R.id.txtClkRV)
        var alarmName: TextView = itemView.findViewById(R.id.alarm_nameRV)
        var alarmActive: Switch = itemView.findViewById(R.id.alarm_activeRV)
        var alarmShare: Button = itemView.findViewById(R.id.alarm_sharing)
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