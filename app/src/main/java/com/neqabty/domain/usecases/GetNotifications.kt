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
        private const val PARAM_SERVICE_ID = "param:serviceID"
        private const val PARAM_TYPE = "param:type"
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getNotifications(serviceID: Int, type: Int, userNumber: Int): Observable<List<NotificationEntity>> {
        val data = HashMap<String, Any>()
        data[PARAM_SERVICE_ID] = serviceID
        data[PARAM_TYPE] = type
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<NotificationEntity>> {
        val serviceID = data?.get(PARAM_SERVICE_ID) as Int
        val type = data?.get(PARAM_TYPE) as Int
        val userNumber = data?.get(PARAM_USER_NUMBER) as Int
        return neqabtyRepository.getNotifications(serviceID, type, userNumber)
    }
}