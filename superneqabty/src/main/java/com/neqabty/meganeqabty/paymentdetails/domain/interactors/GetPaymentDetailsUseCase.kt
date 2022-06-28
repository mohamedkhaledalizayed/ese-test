package com.neqabty.meganeqabty.paymentdetails.domain.interactors


import com.neqabty.meganeqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentdetails.PaymentDetails
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.meganeqabty.paymentdetails.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentDetailsUseCase @Inject constructor(private val repository: PaymentRepository) {
    fun build(id: String, number: String): Flow<ReceiptResponse> {
        return repository.getPaymentDetails(id, number)
    }

    fun build(paymentBody: PaymentBody): Flow<PaymentResponse> {
        return repository.getPaymentInfo(paymentBody)
    }

    fun build(): Flow<PaymentMethodsResponse> {
        return repository.getPaymentMethods()
    }

    fun build(referenceCode: String): Flow<PaymentDetails> {
        return repository.getPaymentDetails(referenceCode)
    }
}