package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class EncryptionRequest(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("description")
    var description: String = ""
) : Request()