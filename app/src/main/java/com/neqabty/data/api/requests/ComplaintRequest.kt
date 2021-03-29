package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class ComplaintRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("cat_id")
    var catId: String = "",
    @SerializedName("sub_cat_id")
    var subCatId: String = "",
    @SerializedName("details")
    var details: String = "",
    @SerializedName("mobile_token")
    var token: String = "",
    @SerializedName("OldRefID")
    var memberNumber: String = ""
) : Request()