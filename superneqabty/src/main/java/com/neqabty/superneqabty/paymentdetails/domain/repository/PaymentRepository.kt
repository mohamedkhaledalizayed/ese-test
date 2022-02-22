package com.neqabty.superneqabty.paymentdetails.domain.repository

import com.neqabty.superneqabty.paymentdetails.data.model.Receipt
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getPaymentDetails(id: String, number: String): Flow<Receipt>
}