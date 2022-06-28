package com.neqabty.meganeqabty.paymentdetails.data.source

import com.neqabty.meganeqabty.paymentdetails.data.api.PaymentApi
import com.neqabty.meganeqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentdetails.PaymentDetails
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentmethods.PaymentMethodsResponse
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi) {

    suspend fun getPaymentDetails(id: String, number: String): ReceiptResponse {
        return paymentApi.getPaymentDetails(id = id, number = number)
    }

    suspend fun getPaymentInfo(paymentBody: PaymentBody): PaymentResponse {
        return paymentApi.getPaymentInfo(paymentBody)
    }

    suspend fun getPaymentMethods(): PaymentMethodsResponse {
        return paymentApi.getPaymentMethods()
    }

    suspend fun getPaymentDetails(referenceCode: String): PaymentDetails {
        return paymentApi.getPaymentDetails(referenceCode)
    }

}