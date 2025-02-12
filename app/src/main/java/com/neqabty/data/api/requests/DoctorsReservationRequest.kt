package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class DoctorsReservationRequest(
    @SerializedName("mobile")
    var mobileNumber: String = ""
) : Request()