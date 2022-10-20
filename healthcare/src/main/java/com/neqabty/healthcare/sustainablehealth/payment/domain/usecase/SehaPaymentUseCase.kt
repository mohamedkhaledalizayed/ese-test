package com.neqabty.healthcare.sustainablehealth.payment.domain.usecase


import com.neqabty.healthcare.sustainablehealth.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.SehaPaymentEntity
import com.neqabty.healthcare.sustainablehealth.payment.domain.repository.SehaPaymentRepository
import com.neqabty.mega.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SehaPaymentUseCase @Inject constructor(private val repository: SehaPaymentRepository) {

    fun build(paymentBody: SehaPaymentBody): Flow<SehaPaymentEntity> {
        return repository.payment(paymentBody)
    }

    fun build(): Flow<List<PaymentMethodEntity>> {
        return repository.getPaymentMethods()
    }

}