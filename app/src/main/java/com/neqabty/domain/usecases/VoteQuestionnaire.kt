package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.QuestionnaireVoteEntity
import io.reactivex.Observable
import javax.inject.Inject

class VoteQuestionnaire @Inject constructor(
    transformer: Transformer<QuestionnaireVoteEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<QuestionnaireVoteEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_QUESTIONNAIRE_ID = "param:questionnaireId"
        private const val PARAM_ANSWER_ID = "param:answerId"
    }

    fun voteQuestionnaire(
        userNumber: String,
        questionnaireId: Int,
        answerId: Int
    ): Observable<QuestionnaireVoteEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_QUESTIONNAIRE_ID] = questionnaireId
        data[PARAM_ANSWER_ID] = answerId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<QuestionnaireVoteEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val questionnaireId = data?.get(PARAM_QUESTIONNAIRE_ID) as Int
        val answerId = data?.get(PARAM_ANSWER_ID) as Int
        return neqabtyRepository.voteQuestionnaire(userNumber ,questionnaireId, answerId)
    }
}