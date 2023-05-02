package com.neqabty.healthcare.chefaa.verifyuser.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class VerificationModel(
    @SerializedName("data")
    val `data`: Any,
    @SerializedName("errors")
    val errors: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)