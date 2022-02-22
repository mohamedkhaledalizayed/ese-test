package com.neqabty.superneqabty.paymentdetails.domain.repository

import com.neqabty.superneqabty.paymentdetails.data.model.ReceiptResponse
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentDetails(id: String, number: String): Flow<ReceiptResponse>
}