package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class NotificationDetailsRequest(
        @SerializedName("request_id")
        var id: String = ""
) : Request()