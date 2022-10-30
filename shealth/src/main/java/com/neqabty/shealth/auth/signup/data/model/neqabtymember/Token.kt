package com.neqabty.shealth.auth.signup.data.model.neqabtymember


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Token(
    @SerializedName("key")
    val key: String
)