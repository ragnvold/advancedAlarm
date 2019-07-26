package com.westwin.advanced_alarm.Diff

import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class VKUserRequest(method: String) : VKRequest<JSONObject>(method) {

    override fun parse(r: JSONObject): JSONObject {
        return r.getJSONObject("response")
    }
}