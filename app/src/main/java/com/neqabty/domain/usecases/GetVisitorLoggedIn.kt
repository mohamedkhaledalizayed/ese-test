package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.UserEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetVisitorLoggedIn @Inject constructor(
    transformer: Transformer<UserEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<UserEntity>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_PASSWORD = "param:password"
        private const val PARAM_TOKEN = "param:token"
    }

    fun login(mobile: String, token: String): Observable<UserEntity> {
        val data = HashMap<String, String>()
        data[PARAM_MOBILE] = mobile
//        data[PARAM_PASSWORD] = password
        data[PARAM_TOKEN] = token
        return observable(data)
    }

//    fun login(mobile: String, password: String, token: String): Observable<UserEntity> {
//        val data = HashMap<String, String>()
//        data[PARAM_MOBILE] = mobile
//        data[PARAM_PASSWORD] = password
//        data[PARAM_TOKEN] = token
//        return observable(data)
//    }

    override fun createObservable(data: Map<String, Any>?): Observable<UserEntity> {
        val mobile = data?.get(PARAM_MOBILE) as String
//        val password = data?.get(PARAM_PASSWORD) as String
        val token = data?.get(PARAM_TOKEN) as String
        return neqabtyRepository.loginVisitor(mobile, token)
    }
}