package com.neqabty.healthcare.commen.mypackages.subscriptiondetails.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DeleteFollowerModel(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)