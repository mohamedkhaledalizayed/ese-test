package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetUserLoggedIn @Inject constructor(
    transformer: Transformer<UserEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<UserEntity>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_MAIN_SYNDICATE = "param:mainSyndicate"
        private const val PARAM_SUB_SYNDICATE = "param:subSyndicate"
        private const val PARAM_TOKEN = "param:token"
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getUserRegistered(mobile: String, mainSyndicateId: Int, subSyndicateId: Int, token: String, userNumber: String): Observable<UserEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_MOBILE] = mobile
        data[PARAM_MAIN_SYNDICATE] = mainSyndicateId
        data[PARAM_SUB_SYNDICATE] = subSyndicateId
        data[PARAM_TOKEN] = token
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<UserEntity> {
        val mobile = data?.get(GetUserLoggedIn.PARAM_MOBILE) as String
        val mainSyndicateId = data?.get(GetUserLoggedIn.PARAM_MAIN_SYNDICATE) as Int
        val subSyndicateId = data?.get(GetUserLoggedIn.PARAM_SUB_SYNDICATE) as Int
        val token = data?.get(GetUserLoggedIn.PARAM_TOKEN) as String
        val userNumber = data?.get(GetUserLoggedIn.PARAM_USER_NUMBER) as String
        return neqabtyRepository.loginUser(mobile, userNumber, token)
    }
}