package com.neqabty.healthcare.payment.domain.interactors

import com.neqabty.healthcare.payment.data.model.inquiryresponse.InquiryModel
import com.neqabty.healthcare.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class GetPaymentDetailsUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun build(id: String, code: String, number: String): Flow<Response<InquiryModel>> {
        return repository.getPaymentDetails(id, code, number)
    }

}