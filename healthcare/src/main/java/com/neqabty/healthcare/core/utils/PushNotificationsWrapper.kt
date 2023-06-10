package com.neqabty.healthcare.core.utils

import android.content.Context
import com.neqabty.healthcare.PushNotificationsHelper

class PushNotificationsWrapper {
    fun getToken(context: Context) { // TODO
        val notificationsHelper = PushNotificationsHelper()
        notificationsHelper.getToken(context)
    }

    fun deleteToken(context: Context) { // TODO
        val notificationsHelper = PushNotificationsHelper()
        notificationsHelper.deleteToken(context)
    }
}