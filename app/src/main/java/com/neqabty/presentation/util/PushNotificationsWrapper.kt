package com.neqabty.presentation.util

import android.content.Context
import com.neqabty.gms.PushNotificationsHelper

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