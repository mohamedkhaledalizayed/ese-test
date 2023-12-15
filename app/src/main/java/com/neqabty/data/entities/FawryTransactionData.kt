package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class FawryTransactionData(
    @field:SerializedName("referenceNumber")
    var referenceNumber: String
) : Response()