package com.neqabty.healthcare.auth.login.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class UserModel(
    @SerializedName("key")
    val key: String,
    @SerializedName("user")
    val user: UserData
)