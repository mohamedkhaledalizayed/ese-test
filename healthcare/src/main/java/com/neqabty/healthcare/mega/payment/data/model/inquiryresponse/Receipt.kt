package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Receipt(
    val inquiry_title: String,
    val net_amount: Double,
    val service_data: List<ServiceData>,
    val status: Boolean,
    val error: String?,
    val total_price: List<TotalPrice>
): Parcelable