package com.neqabty.login.modules.login.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("email")
    val email: String = "",
    @SerializedName("fullname")
    val fullname: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("national_id")
    val nationalId: String = "",
    @SerializedName("password")
    val password: String = ""
)