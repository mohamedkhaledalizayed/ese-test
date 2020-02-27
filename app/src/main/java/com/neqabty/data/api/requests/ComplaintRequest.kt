package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ComplaintRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("complaint_type")
    var type: String = "",
    @SerializedName("details")
    var details: String = "",
    @SerializedName("mobile_token")
    var token: String = ""
) : Request()