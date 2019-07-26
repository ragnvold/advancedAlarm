package com.westwin.advanced_alarm.Models

import android.content.Context
import android.content.SharedPreferences
import com.westwin.advanced_alarm.R

class Social(img: String, var name: String, var state: String) {

    var img: String? = img

    companion object {

        private lateinit var reader: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor

        const val VK = "vk"
        const val FB = "fb"
        const val TW = "tw"
        const val OK = "ok"

        const val VK_logo = "https://icon-icons.com/icons2/1121/PNG/72/1486147202-social-media-circled-network10_79475.png"
        const val FB_logo = "https://icon-icons.com/icons2/642/PNG/72/facebook_icon-icons.com_59205.png"

        private const val IMG = "img"
        private const val STATE = "state"

        fun updateSocial(
            context: Context,
            img: String,
            name: String,
            state: String
        ) {
            editor = context.getSharedPreferences(
                "$name.db",
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(
                IMG,
                img
            )
            editor.putString(
                STATE,
                state
            )
            editor.apply()
        }

        fun updateProfileImage(
            context: Context,
            name: String,
            img: String
        ) {
            editor = context.getSharedPreferences(
                "$name.db",
                Context.MODE_PRIVATE
            ).edit()
            editor.putString(
                IMG,
                img
            )
            editor.apply()
        }

        fun getSocialImg(
            context: Context,
            name: String
        ): String {
            reader = context.getSharedPreferences("$name.db", Context.MODE_PRIVATE)
            if (reader.getString(IMG, "") != "")
                return reader.getString(IMG, "")!!
            when(name) {
                VK -> {
                    return VK_logo
                }
                FB -> {
                    return FB_logo
                }
            }
            return ""
        }

        fun getSocialState(
            context: Context,
            name: String
        ): String {
            reader = context.getSharedPreferences("$name.db", Context.MODE_PRIVATE)
            if (reader.getString(STATE, "")!!.isNotEmpty())
                return reader.getString(STATE, "")!!
            return context.getString(R.string.auth)
        }
    }
}