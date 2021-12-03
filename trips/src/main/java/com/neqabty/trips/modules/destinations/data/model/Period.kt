package com.neqabty.trips.modules.destinations.data.model


import com.google.gson.annotations.SerializedName

data class Period(
    @SerializedName("end_date")
    val endDate: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("repetition")
    val repetition: Repetition = Repetition(),
    @SerializedName("start_date")
    val startDate: String = ""
)