package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
        @SerializedName("mobile")
        var mobile: String = "",
        @SerializedName("current_password")
        var currentPassword: String = "",
        @SerializedName("new_password")
        var newPassword: String = ""
)