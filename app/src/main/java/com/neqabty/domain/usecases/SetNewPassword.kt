package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class SetNewPassword @Inject constructor(
        transformer: Transformer<String>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<String>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_VERIFICATION_CODE = "param:verificationCode"
        private const val PARAM_NEW_PASSWORD = "param:newPassword"
    }

    fun setNewPassword(mobile: String, verificationCode: String, newPassword: String): Observable<String> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobile
        data[PARAM_VERIFICATION_CODE] = verificationCode
        data[PARAM_NEW_PASSWORD] = newPassword
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<String> {
        val mobile = data?.get(PARAM_MOBILE) as String
        val verificationCode = data?.get(PARAM_VERIFICATION_CODE) as String
        val newPassword = data?.get(PARAM_NEW_PASSWORD) as String
        return neqabtyRepository.setNewPassword(mobile, verificationCode, newPassword)
    }
}