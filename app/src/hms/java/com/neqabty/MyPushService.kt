package com.neqabty

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.PreferencesHelper
import org.json.JSONObject

class MyPushService : HmsMessageService() {
    override fun onMessageReceived(message: RemoteMessage?) {
        if (message == null)
            return
        sendNotification(message)
    }

    override fun onNewToken(token: String?) {
        // Obtain a token.
        // Check whether the token is empty.
        token?.let {
            sendRegistrationToServer(token)
        }
    }

    private fun sendRegistrationToServer(token: String) {
//        PreferencesHelper(applicationContext).token = token!!
//        PreferencesHelper(applicationContext).isRegistered = false
        Constants.isFirebaseTokenUpdated.postValue(token)
    }

    private fun sendNotification(remoteMessage: RemoteMessage?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val payload = JSONObject(remoteMessage?.data)

        intent.putExtra("notificationId", payload.get("request_id").toString())
        intent.putExtra("serviceId", payload.get("service_id").toString())

//        var args = Bundle()
//        args.putString("notificationId", remoteMessage!!.data?.get("request_id"))

//        val pendingIntent = NavDeepLinkBuilder(applicationContext)
//                .setGraph(R.navigation.main)
//                .setDestination(R.id.notificationDetailsFragment)
//                .setArguments(args)
//                .createPendingIntent()

        val pendingIntent = TaskStackBuilder.create(applicationContext)
                .addNextIntent(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val channelId = "Notifications"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(payload.get("title").toString())
                .setContentText(payload.get("body").toString())
                .setAutoCancel(true)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    channelId,
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}