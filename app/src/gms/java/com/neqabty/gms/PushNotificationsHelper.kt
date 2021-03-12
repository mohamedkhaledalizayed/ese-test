package com.neqabty.gms

import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import com.neqabty.presentation.common.Constants
import java.io.IOException

class PushNotificationsHelper {

    fun getToken(context: Context) { // TODO
        FirebaseApp.initializeApp(context)
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful)
                        getToken(context)
                    else {
                        Constants.isFirebaseTokenUpdated.postValue(task.result?.token!!)
                    }
                })
    }

    fun deleteToken(context: Context) { // TODO
        Thread(Runnable {
            try {
                FirebaseInstanceId.getInstance().deleteInstanceId()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }
}