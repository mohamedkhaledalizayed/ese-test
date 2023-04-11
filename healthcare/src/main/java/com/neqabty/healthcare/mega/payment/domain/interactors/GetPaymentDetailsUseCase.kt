package com.neqabty.healthcare.mega.payment.domain.interactors

import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetPaymentDetailsUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun build(id: String, code: String, number: String): Flow<Response<ReceiptResponse>> {
        return repository.getPaymentDetails(id, code, number)
    }

}