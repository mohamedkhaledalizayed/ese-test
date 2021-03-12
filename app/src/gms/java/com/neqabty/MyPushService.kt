import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.neqabty.MainActivity
import com.neqabty.R
import com.neqabty.presentation.common.Constants
import com.neqabty.presentation.util.PreferencesHelper

class MyPushService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        sendNotification(remoteMessage)
    }

    override fun onNewToken(token: String?) {
        token?.let {
            if(!token.equals(PreferencesHelper(applicationContext).token))
                sendRegistrationToServer(token)
        }
    }

//    private fun scheduleJob() {
//        val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(this))
//        val myJob = dispatcher.newJobBuilder()
//                .setService(MyJobService::class.java)
//                .setTag("my-job-tag")
//                .build()
//        dispatcher.schedule(myJob)
//    }

    private fun handleNow() {
    }

    private fun sendRegistrationToServer(token: String) {
//        PreferencesHelper(applicationContext).token = token!!
//        PreferencesHelper(applicationContext).isRegistered = false
        Constants.isFirebaseTokenUpdated.postValue(token)
    }

    private fun sendNotification(remoteMessage: RemoteMessage?) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("notificationId", remoteMessage?.data?.get("request_id"))
        intent.putExtra("serviceId", remoteMessage!!.data?.get("service_id"))

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
                .setContentTitle(remoteMessage?.data?.get("title"))
                .setContentText(remoteMessage?.data?.get("body"))
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