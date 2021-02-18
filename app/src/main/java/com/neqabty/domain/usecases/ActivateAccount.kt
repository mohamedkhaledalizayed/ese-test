package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class ActivateAccount @Inject constructor(
    transformer: Transformer<UserEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<UserEntity>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_VERIFICATION_CODE = "param:verificationCode"
        private const val PARAM_PASSWORD = "param:password"
    }

    fun activateAccount(
            mobile: String,
            verificationCode: String,
            password: String
    ): Observable<UserEntity> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobile
        data[PARAM_VERIFICATION_CODE] = verificationCode
        data[PARAM_PASSWORD] = password
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<UserEntity> {
        val mobile = data?.get(PARAM_MOBILE) as String
        val verificationCode = data?.get(PARAM_VERIFICATION_CODE) as String
        val password = data?.get(PARAM_PASSWORD) as String
        return neqabtyRepository.activateAccount(mobile, verificationCode, password)
    }
}