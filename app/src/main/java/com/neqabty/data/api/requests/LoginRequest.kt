package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class LoginRequest(
        @SerializedName("action_type")
        var actionType: String = "",

        @SerializedName("mobile")
        var mobile: String = "",

        @SerializedName("user_number")
        var userNumber: String = "",

        @SerializedName("new_firebase_token")
        var newToken: String = "",

        @SerializedName("old_firebase_token")
        var oldToken: String = ""
)