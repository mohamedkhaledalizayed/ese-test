package com.neqabty.healthcare.sustainablehealth.receipt.domain.repository


import com.neqabty.healthcare.sustainablehealth.receipt.domain.entity.paymentstatus.PaymentStatusEntity
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentStatus(referenceCode: String): Flow<PaymentStatusEntity>
}