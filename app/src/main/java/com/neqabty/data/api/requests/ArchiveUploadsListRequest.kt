package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ArchiveUploadsListRequest(
    @SerializedName("user_id")
    var userNumber: String = "",
    @SerializedName("category_id")
var catId: Int = 0
) : Request()