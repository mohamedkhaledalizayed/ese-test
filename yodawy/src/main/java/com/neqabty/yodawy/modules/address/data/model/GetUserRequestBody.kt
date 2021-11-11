package com.neqabty.yodawy.modules.address.data.model

import com.google.gson.annotations.SerializedName

data class GetUserRequestBody(
    @SerializedName("mobile") val mobileNumber: String,
    @SerializedName("user_number") val userNumber: String
)
