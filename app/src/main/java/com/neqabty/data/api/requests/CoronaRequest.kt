package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class CoronaRequest(
        @SerializedName("syndicate_user_number")
        var userNumber: String = "",
        @SerializedName("phone")
        var phone: String = "",
        @SerializedName("type_of_injury")
        var type: String = "",
        @SerializedName("job")
        var job: String = "",
        @SerializedName("job_destination")
        var work: String = "",
        @SerializedName("treatment_destination")
        var treatmentDestination: String = "",
        @SerializedName("treatment_destination_address")
        var treatmentDestinationAddress: String = "",
        @SerializedName("family_number")
        var family: Int = 0,
        @SerializedName("desc_of_injury")
        var injury: String = "",
        @SerializedName("docs_num")
        var docsNumber: Int = 0
) : Request()