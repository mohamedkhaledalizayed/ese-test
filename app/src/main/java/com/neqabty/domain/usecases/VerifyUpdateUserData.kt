package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.InquireUpdateUserDataEntity
import com.neqabty.domain.entities.VerifyUserDataEntity
import io.reactivex.Observable
import javax.inject.Inject

class VerifyUpdateUserData @Inject constructor(
        transformer: Transformer<VerifyUserDataEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<VerifyUserDataEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:syndicateUserNumber"
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
    }

    fun verifyUser(
        userNumber: String,
        mobileNumber: String
    ): Observable<VerifyUserDataEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<VerifyUserDataEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        return neqabtyRepository.verifyUser(userNumber,mobileNumber)
    }
}