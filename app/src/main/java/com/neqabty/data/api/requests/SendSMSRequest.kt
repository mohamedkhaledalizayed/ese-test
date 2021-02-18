package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class SendSMSRequest(
    @SerializedName("mobile")
    var mobile: String = ""
)