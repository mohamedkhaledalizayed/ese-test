package com.neqabty.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.TextView


fun TextView.openMap(address: String, context: Context){
    val uri = "geo:0,0?q=$address"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    context.startActivity(intent)
}

fun TextView.call(phone: String, context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phone")
    context.startActivity(intent)
}
