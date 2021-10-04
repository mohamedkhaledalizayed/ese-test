package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class QuestionnaireVoteRequest(
        @SerializedName("user_number")
        var user_number: String = "",
        @SerializedName("questionnair_id")
        var questionnaireId: Int = 0,
        @SerializedName("answer_id")
        var answerId: Int = 0,
) : Request()