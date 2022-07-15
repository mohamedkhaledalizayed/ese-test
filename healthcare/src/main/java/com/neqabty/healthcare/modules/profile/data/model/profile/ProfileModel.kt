package com.neqabty.healthcare.modules.profile.data.model.profile


import com.google.gson.annotations.SerializedName

data class ProfileModel(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)