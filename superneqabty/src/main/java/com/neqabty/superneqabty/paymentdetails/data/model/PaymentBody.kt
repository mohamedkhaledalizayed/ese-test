package com.neqabty.superneqabty.paymentdetails.data.model

import com.google.gson.annotations.SerializedName

class PaymentBody (
    @SerializedName("payment")
    val payment: PaymentBodyObject
)

class PaymentBodyObject(
    @SerializedName("service_code")
    val serviceCode: String = "",
    @SerializedName("payment_method")
    val paymentMethod: String = "",
    @SerializedName("payment_source")
    val paymentSource: String = "android",
    @SerializedName("amount")
    val amount: String = "",
    @SerializedName("membership_id")
    val itemId: Int = 0,
    @SerializedName("service_features")
    val service_features: ArrayList<Int> = ArrayList()
)