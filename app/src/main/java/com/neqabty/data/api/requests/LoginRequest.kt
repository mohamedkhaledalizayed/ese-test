package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class LoginRequest(
    @SerializedName("mobile")
    var mobile: String = "",

    @SerializedName("password")
    var password: String = "",

    @SerializedName("user_token")
    var user_token: String = ""
) : Request()