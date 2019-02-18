package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.NotificationEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetNotificationDetails @Inject constructor(transformer: Transformer<NotificationEntity>,
                                                 private val neqabtyRepository: NeqabtyRepository) : UseCase<NotificationEntity>(transformer) {


    companion object {
        private const val PARAM_NOTIFICATION_ID = "param:notificationId"
    }

    fun getNotificationDetails(id: String): Observable<NotificationEntity> {
        val data = HashMap<String, String>()
        data[PARAM_NOTIFICATION_ID] = id
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<NotificationEntity> {
        val id = data?.get(GetNotificationDetails.PARAM_NOTIFICATION_ID) as String
        return neqabtyRepository.getNotificationDetails(id)
    }

}