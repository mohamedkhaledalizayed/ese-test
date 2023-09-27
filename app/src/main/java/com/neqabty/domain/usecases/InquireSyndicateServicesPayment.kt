package com.neqabty.domain.usecases

import com.neqabty.domain.NeqabtyRepository
import com.neqabty.domain.common.Transformer
import com.neqabty.domain.entities.SyndicateServicesPaymentEntity
import io.reactivex.Observable
import javax.inject.Inject

class InquireSyndicateServicesPayment @Inject constructor(
        transformer: Transformer<SyndicateServicesPaymentEntity>,
        private val neqabtyRepository: NeqabtyRepository
) : UseCase<SyndicateServicesPaymentEntity>(transformer) {

    companion object {
        private const val PARAM_USER_NUMBER = "param:userNumber"
        private const val PARAM_USER_NAME = "param:userName"
        private const val PARAM_SERVICE_ID = "param:serviceID"
        private const val PARAM_PAYMENT_TYPE = "param:paymentType"
        private const val PARAM_COUNTRY_ID = "param:countryID"
        private const val PARAM_MOBILE_NUMBER = "param:mobileNumber"
        private const val PARAM_DELIVERY_TYPE = "param:deliveryType"
        private const val PARAM_ADDRESS = "param:address"
        private const val PARAM_MOBILE = "param:mobile"
    }

    fun inquireSyndicateServicesPayment(mobileNumber: String, userNumber: String, userName: String, serviceID: Int, countryID: Int, paymentType: String, locationType: Int, address: String, mobile: String): Observable<SyndicateServicesPaymentEntity> {
        val data = HashMap<String, Any>()
        data[PARAM_USER_NUMBER] = userNumber
        data[PARAM_USER_NAME] = userName
        data[PARAM_SERVICE_ID] = serviceID
        data[PARAM_COUNTRY_ID] = countryID
        data[PARAM_PAYMENT_TYPE] = paymentType
        data[PARAM_MOBILE_NUMBER] = mobileNumber
        data[PARAM_DELIVERY_TYPE] = locationType
        data[PARAM_ADDRESS] = address
        data[PARAM_MOBILE] = mobile
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<SyndicateServicesPaymentEntity> {
        val userNumber = data?.get(PARAM_USER_NUMBER) as String
        val userName = data?.get(PARAM_USER_NAME) as String
        val serviceID = data.get(PARAM_SERVICE_ID) as Int
        val countryID = data.get(PARAM_COUNTRY_ID) as Int
        val paymentType = data.get(PARAM_PAYMENT_TYPE) as String
        val mobileNumber = data?.get(PARAM_MOBILE_NUMBER) as String
        val deliveryType = data?.get(PARAM_DELIVERY_TYPE) as Int
        val address = data?.get(PARAM_ADDRESS) as String
        val mobile = data?.get(PARAM_MOBILE) as String
        return neqabtyRepository.inquireSyndicateServicesPayment(mobileNumber, userNumber, userName, serviceID.toInt(), countryID, paymentType, deliveryType, address, mobile)
    }
}