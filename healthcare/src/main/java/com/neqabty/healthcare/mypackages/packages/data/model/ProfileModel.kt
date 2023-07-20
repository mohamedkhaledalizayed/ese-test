package com.neqabty.healthcare.mypackages.packages.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class ProfileModel(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)