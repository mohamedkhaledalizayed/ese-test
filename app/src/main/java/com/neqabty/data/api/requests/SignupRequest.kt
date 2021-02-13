package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class SignupRequest(
        @SerializedName("user_number")
        var userNumber: String = "",

        @SerializedName("mobile")
        var mobile: String = "",

        @SerializedName("national_id")
        var natID: String = "",

        @SerializedName("firebase_token")
        var newToken: String = "",

        @SerializedName("old_firebase_token")
        var oldToken: String = ""
) : Request()