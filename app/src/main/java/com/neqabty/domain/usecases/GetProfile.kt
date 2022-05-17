package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.ProfileEntity
import io.reactivex.Observable
import javax.inject.Inject

class GetProfile @Inject constructor(
    transformer: Transformer<ProfileEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<ProfileEntity>(transformer) {

    companion object {
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun getProfile(
        mobile: String,
        userNumber: String
    ): Observable<ProfileEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_MOBILE] = mobile
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<ProfileEntity> {
        val mobile = data?.get(PARAM_MOBILE) as String
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.getProfile(mobile, userNumber)
    }
}