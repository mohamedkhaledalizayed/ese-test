package com.neqabty.domain.entities

data class QuestionnaireEntity(
        var id: String = "",
        var question: String = "",
        var is_voted: Boolean = false,
        var total_votings: Int = 0,
        var answers: List<Answer>? = null,
) {
    data class Answer(
            var id: Int = 0,
            var answer: String?,
            var answer_count: Int?
    )
}