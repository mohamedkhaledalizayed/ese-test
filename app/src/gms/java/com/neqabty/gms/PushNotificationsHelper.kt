package com.neqabty.gms

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.neqabty.presentation.common.Constants
import java.io.IOException

class PushNotificationsHelper {

    fun getToken(context: Context) { // TODO
        FirebaseApp.initializeApp(context)
        FirebaseMessaging.getInstance().token
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful)
                        getToken(context)
                    else {
                        Constants.isFirebaseTokenUpdated.postValue(task.result)
                    }
                })
    }

    fun deleteToken(context: Context) { // TODO
        Thread(Runnable {
            try {
                FirebaseMessaging.getInstance().deleteToken()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }
}