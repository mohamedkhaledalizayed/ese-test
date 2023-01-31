package com.neqabty.healthcare.mega.payment.data.model.inquiryresponse


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ReceiptResponse(
    @SerializedName("member")
    val member: Member,
    @SerializedName("receipt")
    val receipt: Receipt?,
    @SerializedName("service")
    val service: Service,
    @SerializedName("title")
    val title: String,
    @SerializedName("delivery_methods")
    val deliveryMethods: List<DeliveryMethod>,
    @SerializedName("gateways_data")
    val gatewaysData: List<GatewaysData>,
    @SerializedName("methods_obj")
    val methodsObj: MethodsObj
)