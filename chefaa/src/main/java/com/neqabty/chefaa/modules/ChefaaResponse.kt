package com.neqabty.chefaa.modules


import com.google.gson.annotations.SerializedName

data class ChefaaResponse<T>(
    @SerializedName("data")
    val responseData: T,
    @SerializedName("message_ar")
    val messageAr: String = "",
    @SerializedName("message_en")
    val messageEn: String = "",
    @SerializedName("status")
    val status: Boolean = false,
    @SerializedName("status_code")
    val statusCode: Int = 0,
    @SerializedName("status_message_ar")
    val statusMessageAr: String = "",
    @SerializedName("status_message_en")
    val statusMessageEn: String = ""
)