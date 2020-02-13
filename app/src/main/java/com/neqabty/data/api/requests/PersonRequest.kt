package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class PersonRequest(
    @SerializedName("name")
    var name: String = "",
    @SerializedName("relationship")
    var relationship: String = "",
    @SerializedName("birth_date")
    var birthDate: String = "",
    @SerializedName("age_on_trip")
    var ageOnTrip: Int = 0
) : Request()
