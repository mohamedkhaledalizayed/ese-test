package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class EncryptionRequest(
    @SerializedName("username")
    var username: String = "",
    @SerializedName("password")
    var password: String = "",
    @SerializedName("description")
    var description: String = ""
)