package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class SetNewPasswordRequest(
        @SerializedName("mobile")
        var mobile: String = "",
        @SerializedName("verification_code")
        var verificationCode: String = "",
        @SerializedName("new_password")
        var newPassword: String = ""
) : Request()