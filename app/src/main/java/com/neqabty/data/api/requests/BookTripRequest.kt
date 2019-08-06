package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class BookTripRequest(
    @SerializedName("main_syndicate_id")
    var mainSyndicate: Int = 0,
    @SerializedName("syndicate_user_number")
    var userNumber: String = "",
    @SerializedName("phone")
    var phone: String = "",
    @SerializedName("trip_id")
    var tripId: Int = 0,
    @SerializedName("regiment_id")
    var regimentId: Int = 0,
    @SerializedName("regiment_date")
    var regimentDate: String = "",
    @SerializedName("housing_type")
    var housingType: String = "",
    @SerializedName("num_child")
    var numChild: Int = 0,
    @SerializedName("ages")
    var ages: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("docs_num")
    var docsNumber: Int = 0
) : Request()
