package com.neqabty.data.mappers

import com.neqabty.data.entities.QuestionnaireVoteData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.QuestionnaireVoteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionnaireVoteDataEntityMapper @Inject constructor() : Mapper<QuestionnaireVoteData, QuestionnaireVoteEntity>() {
    override fun mapFrom(from: QuestionnaireVoteData): QuestionnaireVoteEntity {
        return QuestionnaireVoteEntity(
                message = from.message
        )
    }
}
