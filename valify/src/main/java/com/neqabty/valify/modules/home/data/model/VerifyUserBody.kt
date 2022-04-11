package com.neqabty.valify.modules.home.data.model

import com.google.gson.annotations.SerializedName

class VerifyUserBody (
    @SerializedName("mobile")
    val mobile: String = "",
    @SerializedName("user_number")
    val user_number: String = "",
    @SerializedName("user_name")
    val user_name: String = "android",
    @SerializedName("address")
    val address: String = "",
    @SerializedName("national_id")
    val nationalID: String = ""
    )