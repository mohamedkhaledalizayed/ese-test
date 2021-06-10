package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class ChangeUserMobileData(
    @field:SerializedName("id")
    var id: String,
    @field:SerializedName("mobile")
    var mobile: String,
    @field:SerializedName("name")
    var name: String,
    @field:SerializedName("user_number")
    var userNumber: String,
    @field:SerializedName("msg")
    var msg: String
) : Response()