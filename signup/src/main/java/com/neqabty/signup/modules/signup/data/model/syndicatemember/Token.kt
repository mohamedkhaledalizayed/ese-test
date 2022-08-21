package com.neqabty.signup.modules.signup.data.model.syndicatemember


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Token(
    @SerializedName("key")
    val key: String
)