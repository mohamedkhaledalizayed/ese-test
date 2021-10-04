package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.QuestionnaireEntity
import com.neqabty.presentation.entities.QuestionnaireUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionnaireEntityUIMapper @Inject constructor() : Mapper<QuestionnaireEntity, QuestionnaireUI>() {

    override fun mapFrom(from: QuestionnaireEntity): QuestionnaireUI {
        var questionnaireUI = QuestionnaireUI(
                id = from.id,
                is_voted = from.is_voted,
                question = from.question,
                total_votings = from.total_votings
        )

        from.answers?.let {
            var answers: List<QuestionnaireUI.Answer> = it.map { answerItem ->
                return@map QuestionnaireUI.Answer(
                        id = answerItem.id,
                        answer = answerItem.answer,
                        answer_count = answerItem.answer_count
                )
            }
            questionnaireUI.answers = answers
        }
        return questionnaireUI
    }
}