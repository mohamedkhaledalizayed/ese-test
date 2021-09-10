package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class AdsRequest(
    @SerializedName("add_section_id")
    var sectionId: Int = 0
) : Request()