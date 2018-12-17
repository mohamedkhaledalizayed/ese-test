package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class SyndicateRequest(
    @SerializedName("syndicate_id")
    var syndicateId: String = ""
) : Request()