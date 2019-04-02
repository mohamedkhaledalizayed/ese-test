package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ProviderRequest(
        @SerializedName("provider_type_id")
        var type: String = "",
        @SerializedName("gov_id")
        var govId: String = "",
        @SerializedName("area_id")
        var areaId: String = "",
        @SerializedName("profession_id")
        var professionId: String? = "",
        @SerializedName("degree_id")
        var degreeId: String? = ""
) : Request()