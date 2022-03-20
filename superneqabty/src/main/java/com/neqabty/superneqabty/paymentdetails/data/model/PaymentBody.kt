package com.neqabty.superneqabty.paymentdetails.data.model

import com.google.gson.annotations.SerializedName

class PaymentBody (
    @SerializedName("service_code")
    val serviceCode: String = "",
    @SerializedName("payment_method")
    val paymentMethod: String = "",
    @SerializedName("payment_source")
    val paymentSource: String = "android",
    @SerializedName("amount")
    val amount: String = "",
    @SerializedName("item_id")
    val itemId: Int = 0,
    @SerializedName("callback_success_url")
    val callback_success_url: String = "http://ex.com",
    @SerializedName("callback_fail_url")
    val callback_fail_url: String = "http://ex.com"
)