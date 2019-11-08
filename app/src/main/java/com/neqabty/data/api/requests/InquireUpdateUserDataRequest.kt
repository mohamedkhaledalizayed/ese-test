package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class InquireUpdateUserDataRequest(
    @SerializedName("OldRefID")
    var userNumber: Int = 0
)