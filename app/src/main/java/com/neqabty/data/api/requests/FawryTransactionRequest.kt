package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class FawryTransactionRequest(
        @SerializedName("refrence_id")
        var refrenceId: String = ""
) : Request()