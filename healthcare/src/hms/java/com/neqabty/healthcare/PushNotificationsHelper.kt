package com.neqabty.healthcare

import android.content.Context
import android.text.TextUtils
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.common.ApiException
import com.neqabty.healthcare.core.data.Constants

class PushNotificationsHelper {

    fun getToken(context: Context) { // TODO
        // Create a thread.
        object : Thread() {
            override fun run() {
                try {
                    // Obtain the app ID from the agconnect-service.json file.
                    val appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id")

                    // Enter the token ID HCM.
                    val tokenScope = "HCM"
                    val token = HmsInstanceId.getInstance(context).getToken(appId, tokenScope)

                    // Check whether the token is empty.
                    if (!TextUtils.isEmpty(token)) {
                        Constants.isFirebaseTokenUpdated.postValue(token)
                    } else {
                        getToken(context)
                    }
                } catch (e: ApiException) {
                    getToken(context)
                }
            }
        }.start()
    }

    fun deleteToken(context: Context) { // TODO
        // Create a thread.
        object : Thread() {
            override fun run() {
                try {
                    // Obtain the app ID from the agconnect-service.json file.
                    val appId = AGConnectServicesConfig.fromContext(context).getString("client/app_id")

                    // Enter the token ID HCM.
                    val tokenScope = "HCM"

                    // Delete the token.
                    HmsInstanceId.getInstance(context).deleteToken(appId, tokenScope)
                } catch (e: ApiException) {
                }
            }
        }.start()
    }
}