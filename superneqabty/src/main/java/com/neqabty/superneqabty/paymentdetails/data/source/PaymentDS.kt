package com.neqabty.superneqabty.paymentdetails.data.source

import com.neqabty.superneqabty.paymentdetails.data.api.PaymentApi
import com.neqabty.superneqabty.paymentdetails.data.model.ReceiptResponse
import javax.inject.Inject

class PaymentDS @Inject constructor(private val paymentApi: PaymentApi) {

    suspend fun getPaymentDetails(id: String, number: String): ReceiptResponse {
        return paymentApi.getPaymentDetails(id = id, number = number)
    }

}