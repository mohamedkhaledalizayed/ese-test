package com.neqabty.superneqabty.paymentdetails.data.source

import com.neqabty.superneqabty.paymentdetails.data.api.PaymentApi
import com.neqabty.superneqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.superneqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.superneqabty.paymentdetails.data.model.payment.PaymentResponse
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi) {

    suspend fun getPaymentDetails(id: String, number: String): ReceiptResponse {
        return paymentApi.getPaymentDetails(id = id, number = number)
    }

    suspend fun getPaymentInfo(paymentBody: PaymentBody): PaymentResponse {
        return paymentApi.getPaymentInfo(paymentBody)
    }

}