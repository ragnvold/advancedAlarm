package com.westwin.advanced_alarm.Views.Activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback
import com.westwin.advanced_alarm.Diff.PreferenceUtils
import com.westwin.advanced_alarm.Models.Social
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.requests.VKRequest
import com.westwin.advanced_alarm.Diff.VKUserRequest
import org.json.JSONObject
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.FacebookCallback
import com.vk.api.sdk.auth.VKScope

class SocialsAuthActivity : AppCompatActivity() {

    private lateinit var mDialog: ProgressDialog
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferenceUtils.changeTheme(this)
        super.onCreate(savedInstanceState)
        when (intent.getIntExtra("id", -1)) {
            0 -> {
                VK.login(this, arrayListOf(VKScope.WALL))
            }
            1 -> {
                LoginManager.getInstance().logIn(this, null)
                callbackManager = CallbackManager.Factory.create()
            }
            2 -> {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (intent.getIntExtra("id", -1)) {
            0 -> {
                val callback = object : VKAuthCallback {
                    override fun onLogin(token: VKAccessToken) {
                        VK.execute(VKUserRequest("account.getProfileInfo"), object : VKApiCallback<JSONObject> {
                            override fun success(result: JSONObject) {
                                Social.updateSocial(
                                    this@SocialsAuthActivity,
                                    Social.getSocialImg(
                                        this@SocialsAuthActivity,
                                        Social.VK
                                    ),
                                    Social.VK,
                                    "${result.get("first_name")} ${result.get("last_name")}"
                                )
                            }

                            override fun fail(error: VKApiExecutionException) {
                            }
                        })
                        VK.execute(
                            VKRequest<JSONObject>("photos.get").addParam("album_id", "profile"),
                            object : VKApiCallback<JSONObject> {
                                override fun success(result: JSONObject) {
                                    Social.updateProfileImage(
                                        this@SocialsAuthActivity,
                                        Social.VK,
                                        getProfilePhoto(result)
                                    )
                                    Log.i("TAG", result.toString())
                                }

                                override fun fail(error: VKApiExecutionException) {
                                    Log.i("TAG", error.toString())
                                }
                            })
                        finish()
                    }

                    override fun onLoginFailed(errorCode: Int) {
                        finish()
                    }
                }
                if (data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)) {
                    super.onActivityResult(requestCode, resultCode, data)
                }
            }
            1 -> {
                LoginManager.getInstance().registerCallback(callbackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(loginResult: LoginResult) {
                            Log.i("FB", "Success")
                        }

                        override fun onCancel() {
                            Log.i("FB", "Cancel")
                        }

                        override fun onError(exception: FacebookException) {
                            Log.i("FB", exception.localizedMessage!!)
                        }
                    })
                if(callbackManager.onActivityResult(requestCode, resultCode, data)) {
                    return
                }
            }
        }
    }

    private fun getProfilePhoto(data: JSONObject?): String {
        if (data != null) {
            var temp = data
                .getJSONObject("response")
                .getJSONArray("items")
                .get(0) as JSONObject
            temp = temp
                .getJSONArray("sizes")
                .get(0) as JSONObject
            return temp
                .get("url")
                .toString()
        }
        return ""
    }
}