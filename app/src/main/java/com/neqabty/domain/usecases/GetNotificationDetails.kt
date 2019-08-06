package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.NotificationEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetNotificationDetails @Inject constructor(
        transformer: Transformer<NotificationEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<NotificationEntity>(transformer) {

    companion object {
        private const val PARAM_SERVICE_ID = "param:serviceID"
        private const val PARAM_TYPE = "param:type"
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_REQUEST_ID = "param:requestID"

    }

    fun getNotificationDetails(serviceID: Int, type: Int, userNumber: Int, requestID: Int): Observable<NotificationEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_SERVICE_ID] = serviceID
        data[PARAM_TYPE] = type
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_REQUEST_ID] = requestID
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<NotificationEntity> {
        val serviceID = data?.get(PARAM_SERVICE_ID) as Int
        val type = data?.get(PARAM_TYPE) as Int
        val userNumber = data?.get(PARAM_USER_NUMBER) as Int
        val requestID = data?.get(PARAM_REQUEST_ID) as Int
        return neqabtyRepository.getNotificationDetails(serviceID, type, userNumber, requestID)
    }
}