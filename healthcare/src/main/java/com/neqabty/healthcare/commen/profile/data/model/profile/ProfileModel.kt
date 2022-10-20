package com.neqabty.healthcare.commen.profile.data.model.profile


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ProfileModel(
    @SerializedName("data")
    val `data`: UserData,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)