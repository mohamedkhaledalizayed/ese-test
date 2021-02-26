package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class ForgetPasswordRequest(
        @SerializedName("mobile")
        var mobile: String = "",
        @SerializedName("user_number")
        var userNumber: String = ""
)