package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.QuestionnaireVoteEntity
import com.neqabty.presentation.entities.QuestionnaireVoteUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionnaireVoteEntityUIMapper @Inject constructor() : Mapper<QuestionnaireVoteEntity, QuestionnaireVoteUI>() {

    override fun mapFrom(from: QuestionnaireVoteEntity): QuestionnaireVoteUI {
        return QuestionnaireVoteUI(
                message = from.message
        )
    }
}