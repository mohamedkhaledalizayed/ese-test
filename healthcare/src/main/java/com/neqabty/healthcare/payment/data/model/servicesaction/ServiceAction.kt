package com.neqabty.healthcare.payment.data.model.servicesaction


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

)