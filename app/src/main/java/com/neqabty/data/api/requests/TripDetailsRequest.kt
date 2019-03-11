package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class TripDetailsRequest(
        @SerializedName("trip_id")
        var tripId: String = "2",
        @SerializedName("action")
        var action: String = "8"
) : Request()