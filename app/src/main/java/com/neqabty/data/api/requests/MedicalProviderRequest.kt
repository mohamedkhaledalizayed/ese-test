package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class MedicalProviderRequest(
        @SerializedName("provider_type_id")
        var type: String = "",
        @SerializedName("gov_id")
        var govId: String = "1",
        @SerializedName("area_id")
        var areaId: String = "78"
) : Request()