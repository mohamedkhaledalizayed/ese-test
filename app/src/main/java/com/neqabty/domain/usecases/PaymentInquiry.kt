package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.RenewalPaymentEntity
import io.reactivex.Observable
import javax.inject.Inject

class PaymentInquiry @Inject constructor(
        transformer: Transformer<RenewalPaymentEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<RenewalPaymentEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun paymentInquiry(userNumber: String): Observable<RenewalPaymentEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<RenewalPaymentEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.inquirePayment(userNumber)
    }
}