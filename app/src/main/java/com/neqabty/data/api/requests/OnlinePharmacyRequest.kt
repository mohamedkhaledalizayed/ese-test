package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class OnlinePharmacyRequest(
    @SerializedName("user_number")
    var userNumber: String = ""
) : Request()