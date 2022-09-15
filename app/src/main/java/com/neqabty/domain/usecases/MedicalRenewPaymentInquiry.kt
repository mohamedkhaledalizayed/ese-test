package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalRenewalPaymentEntity
import io.reactivex.Observable
import javax.inject.Inject

class MedicalRenewPaymentInquiry @Inject constructor(
        transformer: Transformer<MedicalRenewalPaymentEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalRenewalPaymentEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
    }

    fun paymentInquiry(userNumber: String): Observable<MedicalRenewalPaymentEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalRenewalPaymentEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        return neqabtyRepository.inquireMedicalRenewalPayment(userNumber)
    }
}