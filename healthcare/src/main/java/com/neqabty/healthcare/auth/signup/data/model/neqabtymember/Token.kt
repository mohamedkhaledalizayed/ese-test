package com.neqabty.healthcare.auth.signup.data.model.neqabtymember


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Token(
    @SerializedName("key")
    val key: String
)