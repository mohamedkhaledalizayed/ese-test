package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ChangeUserMobileRequest(
        @SerializedName("user_number")
        var userNumber: String = "",
        @SerializedName("national_id")
        var natID: String = "",
        @SerializedName("new_mobile")
        var newMobile: String = "",
        @SerializedName("old_mobile")
        var oldMobile: String = "",
) : Request()