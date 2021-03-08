package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class VerifyUserData(
    @field:SerializedName("phone_code")
    var code: String
) : Response()