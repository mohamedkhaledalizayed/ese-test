package com.neqabty.data.mappers

import com.neqabty.data.entities.QuestionnaireData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.QuestionnaireEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionnaireDataEntityMapper @Inject constructor() : Mapper<QuestionnaireData, QuestionnaireEntity>() {

    override fun mapFrom(from: QuestionnaireData): QuestionnaireEntity {
        val questionnaireEntity = QuestionnaireEntity(
                id = from.id,
                is_voted = from.is_voted,
                question = from.question,
                total_votings = from.total_votings
        )

        from.answers?.let {
            var answers: List<QuestionnaireEntity.Answer> = it.map { answerItem ->
                return@map QuestionnaireEntity.Answer(
                        id = answerItem.id,
                        answer = answerItem.answer,
                        answer_count = answerItem.answer_count
                )
            }
            questionnaireEntity.answers = answers
        }
        return questionnaireEntity
    }
}
