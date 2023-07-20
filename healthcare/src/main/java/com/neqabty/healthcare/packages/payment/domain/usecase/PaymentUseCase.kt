package com.neqabty.healthcare.packages.payment.domain.usecase


import com.neqabty.healthcare.packages.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.packages.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.packages.payment.domain.entity.paymentmethods.PaymentEntity
import com.neqabty.healthcare.packages.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PaymentUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun build(paymentBody: SehaPaymentBody): Flow<Response<SehaPaymentResponse>> {
        return repository.payment(paymentBody)
    }

    fun build(code: String): Flow<PaymentEntity> {
        return repository.getPaymentMethods(code)
    }

}