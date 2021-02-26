package com.neqabty.presentation.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class SMSReceiver : BroadcastReceiver() {

    // Get the object of SmsManager
    val sms: SmsManager = SmsManager.getDefault()

    override fun onReceive(context: Context?, intent: Intent) {

        // Retrieves a map of extended data from the intent.
        val bundle = intent.extras
        try {
            if (bundle != null) {
                val pdusObj = bundle["pdus"] as Array<Any>?
                for (i in pdusObj!!.indices) {
                    val currentMessage: SmsMessage = SmsMessage.createFromPdu(pdusObj[i] as ByteArray)
                    val phoneNumber: String = currentMessage.getDisplayOriginatingAddress()
                    var message: String = currentMessage.getDisplayMessageBody().filter { it.isDigit() }
                    val myIntent = Intent("otp")
                    myIntent.putExtra("message", message)
                    LocalBroadcastManager.getInstance(context!!).sendBroadcast(myIntent)
                    // Show Alert
                } // end for loop
            } // bundle is null
        } catch (e: Exception) {
        }
    }
}