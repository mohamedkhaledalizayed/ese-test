package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class MedicalRenewalUpdateData(
    @field:SerializedName("ResultType")
    var requestID: String = ""
) : Response()