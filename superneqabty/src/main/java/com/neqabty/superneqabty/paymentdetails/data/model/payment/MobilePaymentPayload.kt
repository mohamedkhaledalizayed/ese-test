package com.neqabty.superneqabty.paymentdetails.data.model.payment


import com.google.gson.annotations.SerializedName

data class MobilePaymentPayload(
    @SerializedName("callbackUrl")
    val callbackUrl: String,
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("expireAt")
    val expireAt: Int,
    @SerializedName("merchantId")
    val merchantId: String,
    @SerializedName("merchantName")
    val merchantName: String,
    @SerializedName("payAmount")
    val payAmount: String,
    @SerializedName("paymentType")
    val paymentType: String,
    @SerializedName("productDescription")
    val productDescription: String,
    @SerializedName("productName")
    val productName: String,
    @SerializedName("publickey")
    val publickey: String,
    @SerializedName("reference")
    val reference: String,
    @SerializedName("userClientIP")
    val userClientIP: String
)