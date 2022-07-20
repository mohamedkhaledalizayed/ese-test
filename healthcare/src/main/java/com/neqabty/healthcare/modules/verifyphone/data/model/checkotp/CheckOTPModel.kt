package com.neqabty.healthcare.modules.verifyphone.data.model.checkotp


import com.google.gson.annotations.SerializedName

data class CheckOTPModel(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("token")
    val token: String
)