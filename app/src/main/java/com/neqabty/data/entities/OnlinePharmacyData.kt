package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class OnlinePharmacyData(
    @field:SerializedName("type")
    var type: String,
    @field:SerializedName("link")
    var url: String
) : Response()