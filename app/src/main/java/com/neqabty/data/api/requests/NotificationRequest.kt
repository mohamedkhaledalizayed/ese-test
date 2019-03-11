package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class NotificationRequest(
        @SerializedName("user_number")
        var userNumber: String = "",
        @SerializedName("sub_syndicate_id")
        var subSyndicateId: String = ""
) : Request()