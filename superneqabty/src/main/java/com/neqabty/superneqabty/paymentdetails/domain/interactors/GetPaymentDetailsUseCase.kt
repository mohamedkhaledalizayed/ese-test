package com.neqabty.superneqabty.paymentdetails.domain.interactors


import com.neqabty.superneqabty.paymentdetails.data.model.ReceiptResponse
import com.neqabty.superneqabty.paymentdetails.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentDetailsUseCase @Inject constructor(private val repository: PaymentRepository) {
    fun build(id: String, number: String): Flow<ReceiptResponse> {
        return repository.getPaymentDetails(id, number)
    }
}