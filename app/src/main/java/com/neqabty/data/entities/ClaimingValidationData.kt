package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class ClaimingValidationData(
    @field:SerializedName("status_code")
    var code: Int = 0,
    @field:SerializedName("FullName")
    var engineerName: String = "",
    @field:SerializedName("message")
    var message: String?,
    @field:SerializedName("oldbenid")
    var oldbenid: String?
) : Response()
