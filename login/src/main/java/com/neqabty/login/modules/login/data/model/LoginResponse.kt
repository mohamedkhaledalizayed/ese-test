package com.neqabty.login.modules.login.data.model


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("key")
    val key: String,
    @SerializedName("user")
    val user: UserModel
)