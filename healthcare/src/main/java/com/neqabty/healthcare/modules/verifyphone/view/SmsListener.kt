package com.neqabty.healthcare.modules.verifyphone.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log

class SmsListener : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        var messageBody = ""
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent.action) {
            for (smsMessage in Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                messageBody = smsMessage.messageBody
            }
            val local = Intent()
            local.action = "service.to.activity"
            local.putExtra("code", messageBody)
            context.sendBroadcast(local)
        }
    }
}