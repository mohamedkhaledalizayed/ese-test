package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class LoginRequest(
    @SerializedName("user_number")
    var userNumber: String = "",

    @SerializedName("mobile_token")
    var token: String = ""
)