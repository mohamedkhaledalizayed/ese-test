package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class ForgetPassword @Inject constructor(
        transformer: Transformer<String>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<String>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_NAT_ID  = "param:natId"
    }

    fun forgetPassword(mobile: String, userNumber: String, natId: String): Observable<String> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobile
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_NAT_ID] = natId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<String> {
        val mobile = data?.get(PARAM_MOBILE) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val natId = data?.get(PARAM_NAT_ID) as String
        return neqabtyRepository.forgetPassword(mobile, userNumber, natId)
    }
}