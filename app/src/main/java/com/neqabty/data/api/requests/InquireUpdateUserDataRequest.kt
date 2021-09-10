package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class InquireUpdateUserDataRequest(
    @SerializedName("OldRefID")
    var userNumber: String = ""
) : Request()