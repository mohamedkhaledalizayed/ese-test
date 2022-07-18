package com.neqabty.login.modules.login.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class LoginResponse(
    @SerializedName("key")
    val key: String,
    @SerializedName("user")
    val user: UserModel
)