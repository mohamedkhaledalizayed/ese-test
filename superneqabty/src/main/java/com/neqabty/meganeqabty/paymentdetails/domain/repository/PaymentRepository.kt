package com.neqabty.meganeqabty.paymentdetails.domain.repository

import com.neqabty.meganeqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentdetails.PaymentDetails
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentmethods.PaymentMethodsResponse
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentDetails(id: String, number: String): Flow<ReceiptResponse>
    fun getPaymentInfo(paymentBody: PaymentBody): Flow<PaymentResponse>
    fun getPaymentMethods(): Flow<PaymentMethodsResponse>
    fun getPaymentDetails(referenceCode: String): Flow<PaymentDetails>
}