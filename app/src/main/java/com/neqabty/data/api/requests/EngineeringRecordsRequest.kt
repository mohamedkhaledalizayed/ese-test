package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class EngineeringRecordsRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("record_type_id")
    var typeId: String = "",
    @SerializedName("main_syndicate_id")
    var mainSyndicate: String = "",
    @SerializedName("syndicate_user_number")
    var userNumber: String = "",
    @SerializedName("LastRenewYear")
    var lastRenewYear: String = "",
    @SerializedName("RegDataStatusID")
    var statusID: Int = 0,
    @SerializedName("IsOwner")
    var isOwner: Int = 0,
    @SerializedName("docs_num")
    var docsNumber: Int = 0
) : Request()
