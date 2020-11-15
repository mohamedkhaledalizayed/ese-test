package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class HealthCareRequest(
        @SerializedName("oldRefId")
        var userNumber: String = "",
        @SerializedName("deliveryLocation")
        var locationType: Int = 0,
        @SerializedName("deliveryAddress")
        var address: String = "",
        @SerializedName("deliveryPhone")
        var phobe: String = ""
) : Request()