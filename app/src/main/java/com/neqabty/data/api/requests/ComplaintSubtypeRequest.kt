package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ComplaintSubtypeRequest(
        @SerializedName("complaint_type_id")
        var id: String = ""
) : Request()