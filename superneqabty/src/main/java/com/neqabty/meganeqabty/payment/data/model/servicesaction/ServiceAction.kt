package com.neqabty.meganeqabty.payment.data.model.servicesaction


import com.google.gson.annotations.SerializedName

data class ServiceAction(
    @SerializedName("callback_url")
    val callbackUrl: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String,

)