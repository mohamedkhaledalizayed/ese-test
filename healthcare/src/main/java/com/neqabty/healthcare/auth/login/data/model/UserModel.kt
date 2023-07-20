package com.neqabty.healthcare.auth.login.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class UserModel(
    @SerializedName("key")
    val key: String,
    @SerializedName("user")
    val user: UserData
)