package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class DecryptionData(
    @field:SerializedName("result")
    var result: String
) : Response()