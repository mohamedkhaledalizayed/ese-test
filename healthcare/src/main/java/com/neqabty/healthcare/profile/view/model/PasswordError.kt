package com.neqabty.healthcare.profile.view.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PasswordError(
    @SerializedName("error")
    val error: String
)