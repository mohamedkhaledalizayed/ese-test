package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ChangeUserMobileEntity
import io.reactivex.Observable
import javax.inject.Inject

class ChangeUserMobile @Inject constructor(
        transformer: Transformer<ChangeUserMobileEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<ChangeUserMobileEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_NAT_ID = "param:natID"
        private const val PARAM_NEW_MOBILE = "param:newMobile"
        private const val PARAM_OLD_MOBILE = "param:oldMobile"
    }

    fun changeUserMobile(userNumber: String, natID: String, newMobile: String, oldMobile: String): Observable<ChangeUserMobileEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_NAT_ID] = natID
        data[PARAM_NEW_MOBILE] = newMobile
        data[PARAM_OLD_MOBILE] = oldMobile
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ChangeUserMobileEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val natID = data?.get(PARAM_NAT_ID) as String
        val newMobile = data?.get(PARAM_NEW_MOBILE) as String
        val oldMobile = data?.get(PARAM_OLD_MOBILE) as String
        return neqabtyRepository.changeUserMobile(userNumber, natID, newMobile, oldMobile)
    }
}