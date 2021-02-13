package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class SignupUser @Inject constructor(
    transformer: Transformer<UserEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<UserEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_NATID = "param:natId"
        private const val PARAM_NEW_TOKEN = "param:newToken"
        private const val PARAM_OLD_TOKEN = "param:oldToken"
    }

    fun signup(
            userNumber: String,
            mobile: String,
            natID: String,
            newFirebaseToken: String,
            oldFirebaseToken: String
    ): Observable<UserEntity> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobile
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_NATID] = natID
        data[PARAM_NEW_TOKEN] = newFirebaseToken
        data[PARAM_OLD_TOKEN] = oldFirebaseToken
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<UserEntity> {
        val mobile = data?.get(PARAM_MOBILE) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val natID = data?.get(PARAM_NATID) as String
        val newFirebaseToken = data?.get(PARAM_NEW_TOKEN) as String
        val oldFirebaseToken = data?.get(PARAM_OLD_TOKEN) as String
        return neqabtyRepository.signup(userNumber, mobile, natID, newFirebaseToken, oldFirebaseToken)
    }
}