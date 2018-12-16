package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class SignupRequest(
    @SerializedName("email")
    var email: String = "",

    @SerializedName("fname")
    var fName: String = "",

    @SerializedName("lname")
    var lName: String = "",

    @SerializedName("mobile")
    var mobile: String = "",

    @SerializedName("governorate_id")
    var governorateId: String = "",

    @SerializedName("main_syndicate_id")
    var mainSyndicateId: String = "",

    @SerializedName("sub_syndicate_id")
    var subSyndicateId: String = "",

    @SerializedName("password")
    var password: String = ""
) : Request()