package com.neqabty.healthcare.sustainablehealth.receipt.data.model.paymentstatus


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ServiceAction(
    @SerializedName("callback_url")
    val callbackUrl: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("service")
    val service: Service
)