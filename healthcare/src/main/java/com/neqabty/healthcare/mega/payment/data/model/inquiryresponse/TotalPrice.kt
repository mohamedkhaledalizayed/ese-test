package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class TotalPrice(
    val fees: String,
    val gateway_display_name: String,
    val gateway_id: Int,
    val gateway_name: String,
    val payment_method_id: Int,
    val price: Double
): Parcelable