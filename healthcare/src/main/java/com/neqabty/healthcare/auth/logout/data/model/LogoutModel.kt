package com.neqabty.healthcare.auth.logout.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LogoutModel(
    @SerializedName("success")
    val success: Boolean
)