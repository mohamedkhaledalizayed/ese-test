package com.neqabty.healthcare.sustainablehealth.subscribtions.data.model.subscription


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SubscriptionModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)