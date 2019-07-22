package com.westwin.advanced_alarm.Alarm

import org.json.JSONException
import org.json.JSONObject
import java.lang.IllegalStateException
import java.util.*

class Alarm: Comparable<Alarm> {

    var id: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var volume: Int = 0

    var name: String = ""
    var ringtone: String = ""

    var isActive: Boolean = false
    var vibration: Boolean = false

    //init

    constructor(
        id: Int,
        hour: Int,
        minute: Int,
        name: String,
        volume: Int,
        vibration: Boolean,
        ringtone: String,
        isActive: Boolean
    ) {
        this.id = id
        this.hour = hour
        this.minute = minute
        this.name = name
        this.volume = volume
        this.vibration = vibration
        this.ringtone = ringtone
        this.isActive = isActive
    }

    constructor()

    //Json converter

    fun toJSON(): String {
        val alarm = JSONObject()
        try {
            with(alarm) {
                put(ID, id)
                put(HOUR, hour)
                put(MINUTE, minute)
                put(NAME, name)
                put(VOLUME, volume)
                put(VIBRATION, vibration)
                put(RINGTONE, ringtone)
                put(ISACTIVE, isActive)
            }
        } catch (except: JSONException) {
            throw IllegalStateException("Failed to convert in JSON")
        }
        return alarm.toString()
    }

    fun fromJSON(value: String): Alarm {
        val alarm = Alarm()
        try {
            val json = JSONObject(value)
            with(alarm) {
                id = json.getInt(ID)
                hour = json.getInt(HOUR)
                minute = json.getInt(MINUTE)
                name = json.getString(NAME)
                volume = json.getInt(VOLUME)
                vibration = json.getBoolean(VIBRATION)
                ringtone = json.getString(RINGTONE)
                isActive = json.getBoolean(ISACTIVE)
            }
        } catch (except: JSONException) {
            throw IllegalStateException("Failed to convert in Alarm")
        }
        return alarm
    }

    override fun toString(): String {
        return "Alarm{" +
                "id=" + id +
                ", hour=" + hour +
                ", minute=" + minute +
                ", name=" + name +
                ", volume=" + volume +
                ", vibration=" + vibration +
                ", ringtone=" + ringtone +
                ", isActive=" + isActive +
                '}'.toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other !is Alarm) {
            return false
        }
        return id == other.id &&
                hour == other.hour &&
                minute == other.minute &&
                name == other.name &&
                volume == other.volume &&
                vibration == other.vibration &&
                ringtone == other.ringtone &&
                isActive == other.isActive
    }

    override fun hashCode(): Int {
        return Objects.hash(
            id,
            hour,
            minute,
            name,
            volume,
            vibration,
            ringtone,
            isActive
        )
    }

    override fun compareTo(other: Alarm): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val otherCal = Calendar.getInstance()
        otherCal.set(Calendar.HOUR_OF_DAY, other.hour)
        otherCal.set(Calendar.MINUTE, other.minute)
        return calendar.compareTo(otherCal)
    }

    companion object {
        private const val ID: String = "id"
        private const val NAME: String = "name"
        private const val HOUR: String = "hour"
        private const val MINUTE: String = "minute"
        private const val ISACTIVE: String = "isActive"
        private const val RINGTONE: String = "ringtone"
        private const val VOLUME: String = "volume"
        private const val VIBRATION: String = "vibration"
    }
}