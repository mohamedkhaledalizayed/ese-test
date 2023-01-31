package com.neqabty.healthcare.auth.forgetpassword.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ChangePasswordBody(
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)