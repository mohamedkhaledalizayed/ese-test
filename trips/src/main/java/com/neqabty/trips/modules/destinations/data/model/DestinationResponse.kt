package com.neqabty.trips.modules.destinations.data.model

import com.google.gson.annotations.SerializedName

data class DestinationResponse(
    @SerializedName("destinations")
    val destinations: List<DestinationModel>
)
