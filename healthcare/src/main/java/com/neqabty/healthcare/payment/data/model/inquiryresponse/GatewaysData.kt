package com.neqabty.healthcare.payment.data.model.inquiryresponse


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class GatewaysData(
    val created_at: String,
    val display_name: String,
    val endpoint_url: String,
    val gateway: String,
    val id: Int,
    val inquiry_url: String,
    val is_active: Boolean,
    val name: String,
    val order: String?,
    val updated_at: String
): Parcelable