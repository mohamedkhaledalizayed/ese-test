package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class LoginVisitorRequest(
    @SerializedName("mobile")
    var mobile: String = ""
) : Request()