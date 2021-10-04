package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class QuestionnaireRequest(
        @SerializedName("user_number")
        var user_number: String = ""
) : Request()