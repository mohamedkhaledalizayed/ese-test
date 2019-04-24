package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.NotificationEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetNotifications @Inject constructor(
    transformer: Transformer<List<NotificationEntity>>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<List<NotificationEntity>>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_SYNDICATE_ID = "param:syndicateID"
    }

    fun getNotifications(userNumber: String, subSyndicateId: String): Observable<List<NotificationEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_SYNDICATE_ID] = subSyndicateId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<NotificationEntity>> {
        val userNumber = data?.get(GetNotifications.PARAM_USER_NUMBER) as String
        val subSyndicateId = data?.get(GetNotifications.PARAM_SYNDICATE_ID) as String
        return neqabtyRepository.getNotifications(userNumber, subSyndicateId)
    }
}