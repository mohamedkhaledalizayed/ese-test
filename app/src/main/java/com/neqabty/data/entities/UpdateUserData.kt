package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class UpdateUserData(
    @field:SerializedName("message")
    var message: String
) : Response()