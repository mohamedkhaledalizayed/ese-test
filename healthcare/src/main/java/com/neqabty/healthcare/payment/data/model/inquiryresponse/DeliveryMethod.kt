package com.neqabty.healthcare.payment.data.model.inquiryresponse


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class DeliveryMethod(
    val created_at: String,
    val id: Int,
    val method: String,
    val method_id: String?,
    val price: String,
    val service: Int,
    val type: String,
    val updated_at: String
): Parcelable