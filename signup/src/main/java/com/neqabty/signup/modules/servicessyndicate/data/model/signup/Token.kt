package com.neqabty.signup.modules.servicessyndicate.data.model.signup


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Token(
    @SerializedName("key")
    val key: String
)