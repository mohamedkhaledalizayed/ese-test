package com.neqabty.superneqabty.paymentdetails.domain.repository

import com.neqabty.superneqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.superneqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.superneqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.superneqabty.paymentdetails.data.model.paymentmethods.PaymentMethodsResponse
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentDetails(id: String, number: String): Flow<ReceiptResponse>
    fun getPaymentInfo(paymentBody: PaymentBody): Flow<PaymentResponse>
    fun getPaymentMethods(): Flow<PaymentMethodsResponse>
}