package com.neqabty.yodawy.modules.address.data.model


import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("data")
    val dataModel: T,
    @SerializedName("message_ar")
    val messageAr: String,
    @SerializedName("message_en")
    val messageEn: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message_ar")
    val statusMessageAr: String,
    @SerializedName("status_message_en")
    val statusMessageEn: String
)