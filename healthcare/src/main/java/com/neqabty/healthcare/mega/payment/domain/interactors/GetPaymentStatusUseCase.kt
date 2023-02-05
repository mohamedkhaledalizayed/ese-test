package com.neqabty.healthcare.mega.payment.domain.interactors

import com.neqabty.healthcare.mega.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentStatusUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun build(referenceCode: String): Flow<PaymentStatusEntity> {
        return repository.getPaymentStatus(referenceCode)
    }

}