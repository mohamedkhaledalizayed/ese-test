package com.neqabty.healthcare.mypackages.addfollower.data.model.addfollower


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AddFollowerModel(
    @SerializedName("data")
    val `data`: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)