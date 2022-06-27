package com.neqabty.healthcare.modules.subscribtions.data.model.subscription


import com.google.gson.annotations.SerializedName

data class SubscriptionModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)