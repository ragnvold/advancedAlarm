package com.westwin.advanced_alarm.Diff

import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.VKMethodCall
import com.vk.api.sdk.internal.ApiCommand

class VKWallPostCommand: ApiCommand<Int>() {

    override fun onExecute(manager: VKApiManager): Int {
        val callBuilder = VKMethodCall.Builder()
            .method("wall.post")
            .version(manager.config.version)

        return manager.execute(callBuilder.build(), null)
    }
}