package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class HealthCareProjectStatusData(
    @field:SerializedName("status")
    var status: Int,
    @field:SerializedName("status_message")
    var statusMsg: String? = ""
) : Response()