package com.neqabty.healthcare.mypackages.subscriptiondetails.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteFollowerModel(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)