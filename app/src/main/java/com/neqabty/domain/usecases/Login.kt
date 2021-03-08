package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class Login @Inject constructor(
        transformer: Transformer<UserEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<UserEntity>(transformer) {

    companion object {
        private const val PARAM_ACTION_TYPE = "param:actionType"
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_NEW_TOKEN = "param:newToken"
        private const val PARAM_OLD_TOKEN = "param:oldToken"
        private const val PARAM_PASSWORD = "param:password"
        const val PARAM_ACTION_LOGIN = "login"
        const val PARAM_ACTION_VERIFIED_LOGIN = "verifiedLogin"
        const val PARAM_ACTION_UPDATE_TOKEN = "updateFirebaseToken"
    }

    fun login(actionType: String, mobile: String, userNumber: String, newToken: String, oldToken: String, password: String): Observable<UserEntity> {
        val data = HashMap<String, String>()
        data[PARAM_ACTION_TYPE] = actionType
        data[PARAM_MOBILE] = mobile
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_NEW_TOKEN] = newToken
        data[PARAM_OLD_TOKEN] = oldToken
        data[PARAM_PASSWORD] = password
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<UserEntity> {
        val actionType = data?.get(PARAM_ACTION_TYPE) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val newToken = data?.get(PARAM_NEW_TOKEN) as String
        val oldToken = data?.get(PARAM_OLD_TOKEN) as String
        val password = data?.get(PARAM_PASSWORD) as String
        return neqabtyRepository.login(actionType, mobile, userNumber, newToken, oldToken, password)
    }
}