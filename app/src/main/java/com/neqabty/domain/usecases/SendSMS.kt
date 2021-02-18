package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.VerifyUserDataEntity
import io.reactivex.Observable
import javax.inject.Inject

class SendSMS @Inject constructor(
    transformer: Transformer<Unit>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<Unit>(transformer) {

    companion object {
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
    }

    fun sendSMS(
        mobileNumber: String
    ): Observable<Unit> {
        val data = HashMap<String, Any>()
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<Unit> {
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        return neqabtyRepository.sendSMS(mobileNumber)
    }
}