package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.QuestionnaireEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetQuestionnaires @Inject constructor(
    transformer: Transformer<QuestionnaireEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<QuestionnaireEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
    }

    fun getQuestionnaires(
        userNumber: String
    ): Observable<QuestionnaireEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<QuestionnaireEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getQuestionnaires(userNumber)
    }
}