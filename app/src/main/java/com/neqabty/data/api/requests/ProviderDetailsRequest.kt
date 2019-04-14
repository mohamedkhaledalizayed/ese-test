package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ProviderDetailsRequest(
        @SerializedName("provider_id")
        var providerId: String = "",
        @SerializedName("provider_type_id")
        var type: String = ""
) : Request()