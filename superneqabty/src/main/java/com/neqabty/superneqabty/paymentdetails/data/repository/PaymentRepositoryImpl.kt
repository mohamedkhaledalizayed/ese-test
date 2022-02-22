package com.neqabty.superneqabty.paymentdetails.data.repository


import com.neqabty.superneqabty.paymentdetails.data.model.ReceiptResponse
import com.neqabty.superneqabty.paymentdetails.data.source.PaymentDS
import com.neqabty.superneqabty.paymentdetails.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val paymentDS: PaymentDS) : PaymentRepository {
    override fun getPaymentDetails(id: String, number: String): Flow<ReceiptResponse> {
        return flow {
            emit(paymentDS.getPaymentDetails(id, number))
        }
    }


}
