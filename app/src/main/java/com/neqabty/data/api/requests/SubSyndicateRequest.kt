package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class SubSyndicateRequest(
    @SerializedName("main_syndicate")
    var mainSyndicateId: String = "",
    @SerializedName("action")
    var action: String = "4"
) : Request()