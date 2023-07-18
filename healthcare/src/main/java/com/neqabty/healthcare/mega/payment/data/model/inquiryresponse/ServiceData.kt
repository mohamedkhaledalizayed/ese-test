package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ServiceData(
    val key: String,
    val key_label: String,
    val key_value: Double
): Parcelable