package com.neqabty.courses.reservations.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Links(
    @SerializedName("appointments")
    val appointments: String,
    @SerializedName("pricings")
    val pricings: String
)