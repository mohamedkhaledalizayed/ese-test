package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class RegisterRequest(
    @SerializedName("mobile")
    var mobile: String = "",

    @SerializedName("main_syndicate")
    var mainSyndicate: Int = 0,

    @SerializedName("sub_syndicate")
    var subSyndicate: Int = 0,

    @SerializedName("mobile_token")
    var token: String = "",

    @SerializedName("syndicate_user_number")
    var userNumber: String = ""
) : Request()