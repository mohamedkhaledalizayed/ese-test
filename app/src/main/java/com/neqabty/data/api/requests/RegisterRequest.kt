package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class RegisterRequest(
    @SerializedName("mobile")
    var mobile: String = "",

    @SerializedName("main_syndicate")
    var mainSyndicate: String = "",

    @SerializedName("sub_syndicate")
    var subSyndicate: String = "",

    @SerializedName("mobile_token")
    var token: String = ""
) : Request()