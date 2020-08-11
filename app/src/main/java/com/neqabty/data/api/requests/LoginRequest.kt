package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("user_number")
    var userNumber: String = "",

    @SerializedName("mobile_token")
    var token: String = ""
)