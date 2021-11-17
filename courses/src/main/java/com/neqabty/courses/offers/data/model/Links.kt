package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("appointments")
    val appointments: String,
    @SerializedName("pricing")
    val pricing: String,
    @SerializedName("reservations")
    val reservations: String
)