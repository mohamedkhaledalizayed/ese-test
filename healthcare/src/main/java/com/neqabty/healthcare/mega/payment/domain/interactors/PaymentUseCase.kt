package com.neqabty.healthcare.mega.payment.domain.interactors

import com.neqabty.healthcare.mega.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun build(paymentBody: Any): Flow<PaymentEntity> {
        return repository.payment(paymentBody)
    }

}