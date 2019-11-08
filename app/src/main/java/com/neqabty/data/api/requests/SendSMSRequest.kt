package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class SendSMSRequest(
        @SerializedName("OldRefID")
        var userNumber: String = "",
        @SerializedName("phone_number")
        var phoneNumber: String = ""
)