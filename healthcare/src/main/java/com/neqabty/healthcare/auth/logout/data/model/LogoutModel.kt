package com.neqabty.healthcare.auth.logout.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LogoutModel(
    @SerializedName("success")
    val success: Boolean
)