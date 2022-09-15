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
        private const val PARAM_USER_NAME = "param:userName"
        private const val PARAM_SERVICE_ID = "param:serviceID"
        private const val PARAM_PAYMENT_TYPE = "param:paymentType"
        private const val PARAM_PAYMENT_GATEWAY_ID = "param:paymentGatewayId"
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
        private const val PARAM_DELIVERY_TYPE = "param:deliveryType"
        private const val PARAM_ADDRESS = "param:address"
        private const val PARAM_MOBILE = "param:mobile"
        private const val PARAM_IS_INQUIRE = "param:isInquire"
    }

    fun paymentInquiry(isInquire: Boolean, mobileNumber: String, userNumber: String, userName: String, serviceID: Int, paymentType: String, paymentGatewayId: Int, locationType: Int, address: String, mobile: String): Observable<MedicalRenewalPaymentEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_USER_NAME] = userName
        data[PARAM_SERVICE_ID] = serviceID
        data[PARAM_PAYMENT_TYPE] = paymentType
        data[PARAM_PAYMENT_GATEWAY_ID] = paymentGatewayId
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        data[PARAM_DELIVERY_TYPE] = locationType
        data[PARAM_ADDRESS] = address
        data[PARAM_MOBILE] = mobile
        data[PARAM_IS_INQUIRE] = isInquire
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<MedicalRenewalPaymentEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val userName = data?.get(PARAM_USER_NAME) as String
        val serviceID = data.get(PARAM_SERVICE_ID) as Int
        val paymentType = data.get(PARAM_PAYMENT_TYPE) as String
        val paymentGatewayId = data.get(PARAM_PAYMENT_GATEWAY_ID) as Int
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        val deliveryType = data?.get(PARAM_DELIVERY_TYPE) as Int
        val address = data?.get(PARAM_ADDRESS) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        val isInquire = data?.get(PARAM_IS_INQUIRE) as Boolean
        return neqabtyRepository.inquireMedicalRenewalPayment(isInquire, mobileNumber, userNumber, userName, serviceID.toInt(), paymentType, paymentGatewayId, deliveryType, address, mobile)
    }
}