package com.neqabty.domain.usecases

import com.neqabty.data.entities.MedicalRenewalPaymentData
import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MedicalRenewalPaymentEntity
import com.neqabty.domain.entities.MemberEntity
import io.reactivex.Observable
import javax.inject.Inject

class PaymentInquiry @Inject constructor(
    transformer: Transformer<MedicalRenewalPaymentEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MedicalRenewalPaymentEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_SERVICE_ID = "param:serviceID"
        private const val PARAM_REQUEST_ID = "param:requestID"
        private const val PARAM_AMOUNT = "param:amount"
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
    }

    fun paymentInquiry(mobileNumber: String, userNumber: String, serviceID: String, requestID: String, amount: String): Observable<MedicalRenewalPaymentEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_SERVICE_ID] = serviceID
        data[PARAM_REQUEST_ID] = requestID
        data[PARAM_AMOUNT] = amount
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalRenewalPaymentEntity> {
        val userNumber = data?.get(PaymentInquiry.PARAM_USER_NUMBER) as String
        val serviceID = data.get(PaymentInquiry.PARAM_SERVICE_ID) as String
        val requestID = data.get(PaymentInquiry.PARAM_REQUEST_ID) as String
        val amount = data.get(PaymentInquiry.PARAM_AMOUNT) as String
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        return neqabtyRepository.inquirePayment(mobileNumber, userNumber, serviceID.toInt(), requestID, amount)
    }
}