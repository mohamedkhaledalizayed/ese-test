package com.neqabty.shealth.sustainablehealth.profile.view.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class PasswordError(
    @SerializedName("error")
    val error: String
)