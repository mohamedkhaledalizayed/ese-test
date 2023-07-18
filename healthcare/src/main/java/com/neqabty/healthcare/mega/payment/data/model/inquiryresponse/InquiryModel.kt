package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class InquiryModel(
    val delivery_methods: List<DeliveryMethod>,
    val gateway_parameters: List<GatewayParameter>,
    val gateways_data: List<GatewaysData>,
    val member: String,
    val receipt: Receipt,
    val service: String,
    val title: String
): Parcelable