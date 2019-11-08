package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.AppVersionEntity
import com.neqabty.domain.entities.NotificationsCountEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetNotificationsCount @Inject constructor(
        transformer: Transformer<NotificationsCountEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<NotificationsCountEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getNotificationsCount(userNumber: String): Observable<NotificationsCountEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }
    override fun createObservable(data: Map<String, Any>?): Observable<NotificationsCountEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getNotificationsCount(userNumber)
    }
}