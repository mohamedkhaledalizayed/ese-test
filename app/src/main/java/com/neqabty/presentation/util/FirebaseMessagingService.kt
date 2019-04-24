package com.neqabty.presentation.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.neqabty.MainActivity
import com.neqabty.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        sendNotification(remoteMessage)
    }

    override fun onNewToken(token: String?) {
        sendRegistrationToServer(token)
    }

    private fun scheduleJob() {
        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
        val myJob = dispatcher.newJobBuilder()
                .setService(MyJobService::class.java)
                .setTag("my-job-tag")
                .build()
        dispatcher.schedule(myJob)
    }

    private fun handleNow() {
    }

    private fun sendRegistrationToServer(token: String?) {
        PreferencesHelper(applicationContext).token = token
        PreferencesHelper(applicationContext).isRegistered = false
    }

    private fun sendNotification(remoteMessage: RemoteMessage?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("notificationId", remoteMessage?.data?.get("request_id"))

        var args = Bundle()
        args.putString("notificationId", remoteMessage!!.data?.get("request_id"))

//        val pendingIntent = NavDeepLinkBuilder(applicationContext)
//                .setGraph(R.navigation.main)
//                .setDestination(R.id.notificationDetailsFragment)
//                .setArguments(args)
//                .createPendingIntent()

        val pendingIntent = TaskStackBuilder.create(applicationContext)
                .addNextIntent(intent)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        val channelId = getString(com.neqabty.R.string.app_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(com.neqabty.R.drawable.logo)
                .setContentTitle(remoteMessage?.data?.get("title"))
                .setContentText(remoteMessage?.data?.get("body"))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    getString(R.string.app_name),
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}