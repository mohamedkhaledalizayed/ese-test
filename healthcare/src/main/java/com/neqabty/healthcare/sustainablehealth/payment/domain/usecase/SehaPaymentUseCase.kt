package com.neqabty.healthcare.sustainablehealth.payment.domain.usecase


import com.neqabty.healthcare.sustainablehealth.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentMethodEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.repository.SehaPaymentRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class SehaPaymentUseCase @Inject constructor(private val repository: SehaPaymentRepository) {

    fun build(paymentBody: SehaPaymentBody): Flow<Response<SehaPaymentResponse>> {
        return repository.payment(paymentBody)
    }

    fun build(code: String): Flow<PaymentEntity> {
        return repository.getPaymentMethods(code)
    }

}