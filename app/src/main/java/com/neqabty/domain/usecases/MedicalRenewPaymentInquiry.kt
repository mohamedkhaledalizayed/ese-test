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
        private const val PARAM_DELIVERY_TYPE = "param:deliveryType"
        private const val PARAM_ADDRESS = "param:address"
        private const val PARAM_MOBILE = "param:mobile"
    }

    fun paymentInquiry(userNumber: String, deliveryType: Int, address: String, mobile: String): Observable<MedicalRenewalPaymentEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_DELIVERY_TYPE] = deliveryType
        data[PARAM_ADDRESS] = address
        data[PARAM_MOBILE] = mobile
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalRenewalPaymentEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val deliveryType = data?.get(PARAM_DELIVERY_TYPE) as Int
        val address = data?.get(PARAM_ADDRESS) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        return neqabtyRepository.inquireMedicalRenewalPayment(userNumber, deliveryType, address, mobile)
    }
}