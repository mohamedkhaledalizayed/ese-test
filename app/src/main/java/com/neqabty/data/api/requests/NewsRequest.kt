package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class NewsRequest(
    @SerializedName("main_syndicate")
    var syndicateId: String = "",
    @SerializedName("action")
    var action: String = "1"
) : Request()