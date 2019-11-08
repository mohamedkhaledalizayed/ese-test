package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class UpdateUserDataRequest(
        @SerializedName("OldRefID")
        var userNumber: Int = 0,
        @SerializedName("full_name")
        var fullName: String = "",
        @SerializedName("national_id")
        var nationalID: String = "",
        @SerializedName("gender")
        var gender: String = "",
        @SerializedName("user_id")
        var userID: String = ""
)