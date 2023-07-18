package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class GatewayParameter(
    val entity: String,
    val gateway: String,
    val id: Int,
    val merchant_id: String,
    val public_key: String,
    val service_category: String
): Parcelable