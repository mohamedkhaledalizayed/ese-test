package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class UpdateUserDataRequest(
        @SerializedName("OldRefID")
        var userNumber: Int = 0,
        @SerializedName("name")
        var fullName: String = "",
        @SerializedName("nationalNumber")
        var nationalID: String = "",
        @SerializedName("mobile")
        var mobile: String = "",
        @SerializedName("docs_num")
        var docsNumber: Int = 0
)