package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class QuestionnaireData(
        @field:SerializedName("id")
        var id: String = "",
        @field:SerializedName("question")
        var question: String = "",
        @field:SerializedName("is_voted")
        var is_voted: Boolean = false,
        @field:SerializedName("total_votings")
        var total_votings: Int = 0,
        @field:SerializedName("answers")
        var answers: List<Answer>? = null
) : Response() {
    data class Answer(
            @field:SerializedName("id")
            var id: Int = 0,
            @field:SerializedName("answer")
            var answer: String?,
            @field:SerializedName("answer_count")
            var answer_count: Int?
    )
}