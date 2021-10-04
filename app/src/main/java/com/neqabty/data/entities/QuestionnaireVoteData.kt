package com.neqabty.data.entities
import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class QuestionnaireVoteData(
    @field:SerializedName("message")
    var message: String
) : Response()