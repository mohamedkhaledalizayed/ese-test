package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ServicesRequest(
    @SerializedName("service_id")
    var typeID: Int = 0
) : Request()