package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class AdsRequest(
    @SerializedName("platform")
    var platform: String = "mobile",
    @SerializedName("add_section_id")
    var sectionId: Int = 0
)