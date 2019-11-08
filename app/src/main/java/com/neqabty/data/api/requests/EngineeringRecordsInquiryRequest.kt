package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class EngineeringRecordsInquiryRequest(
    @SerializedName("user_number")
    var userNumber: Int = 0
)