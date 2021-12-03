package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class DoctorsReservationData(
    @field:SerializedName("auth_code")
    var authCode: String
) : Response()