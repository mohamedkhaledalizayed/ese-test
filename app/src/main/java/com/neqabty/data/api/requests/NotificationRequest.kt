package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class NotificationRequest(
//    @SerializedName("service_id")
//    var serviceID: Int = 0,
//    @SerializedName("type")
//    var type: Int = 0,
    @SerializedName("user_number")
    var userNumber: Int = 0
//    @SerializedName("request_id")
//    var requestID: Int = 0
) : Request()