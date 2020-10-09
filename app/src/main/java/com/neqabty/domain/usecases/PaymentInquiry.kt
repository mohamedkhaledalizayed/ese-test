package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.MemberEntity
import io.reactivex.Observable
import javax.inject.Inject

class PaymentInquiry @Inject constructor(
    transformer: Transformer<MemberEntity>,
    private val neqabtyRepository: NeqabtyRepository
) : UseCase<MemberEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_SERVICE_ID = "param:serviceID"
        private const val PARAM_REQUEST_ID = "param:requestID"
        private const val PARAM_AMOUNT = "param:amount"
    }

    fun paymentInquiry(userNumber: String, serviceID: String, requestID: String, amount: String): Observable<MemberEntity> {
        val data = HashMap<String, String>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_SERVICE_ID] = serviceID
        data[PARAM_REQUEST_ID] = requestID
        data[PARAM_AMOUNT] = amount
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MemberEntity> {
        val userNumber = data?.get(PaymentInquiry.PARAM_USER_NUMBER) as String
        val serviceID = data.get(PaymentInquiry.PARAM_SERVICE_ID) as String
        val requestID = data.get(PaymentInquiry.PARAM_REQUEST_ID) as String
        val amount = data.get(PaymentInquiry.PARAM_AMOUNT) as String
        return neqabtyRepository.inquirePayment(userNumber, serviceID.toInt(), requestID, amount)
    }
}