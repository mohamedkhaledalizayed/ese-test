package com.neqabty.superneqabty.paymentdetails.domain.repository

import com.neqabty.superneqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.superneqabty.paymentdetails.data.model.ReceiptResponse
import com.neqabty.superneqabty.paymentdetails.data.model.payment.PaymentResponse
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentDetails(id: String, number: String): Flow<ReceiptResponse>
    fun getPaymentInfo(paymentBody: PaymentBody): Flow<PaymentResponse>
}