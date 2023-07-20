package com.neqabty.healthcare.profile.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProfileModel(
    @SerializedName("data")
    val `data`: UserData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)