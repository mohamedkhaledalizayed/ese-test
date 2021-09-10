package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class UpdateUserDataRequest(
    @SerializedName("OldRefID")
    var userNumber: String = "",
    @SerializedName("name")
    var fullName: String = "",
    @SerializedName("nationalNumber")
    var nationalID: String = "",
    @SerializedName("mobile")
    var mobile: String = "",
    @SerializedName("docs_num")
    var docsNumber: Int = 0
) : Request()