package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class EncryptionData(
    @field:SerializedName("data")
    var encryption: String
) : Response()