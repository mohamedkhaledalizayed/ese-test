package com.neqabty.shealth.sustainablehealth.receipt.domain.interactors



import com.neqabty.shealth.sustainablehealth.receipt.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.shealth.sustainablehealth.receipt.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentUseCase @Inject constructor(private val repository: PaymentRepository) {

    fun build(referenceCode: String): Flow<PaymentStatusEntity> {
        return repository.getPaymentStatus(referenceCode)
    }

}