package com.neqabty.healthcare.auth.forgetpassword.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ChangePasswordModel(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)