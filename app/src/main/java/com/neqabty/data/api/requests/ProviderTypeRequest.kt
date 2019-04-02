package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ProviderTypeRequest(
        @SerializedName("medical_type")
        var type: String = ""
) : Request()