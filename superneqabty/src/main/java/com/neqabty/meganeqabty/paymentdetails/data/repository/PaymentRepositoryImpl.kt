package com.neqabty.meganeqabty.paymentdetails.data.repository


import com.neqabty.meganeqabty.paymentdetails.data.model.PaymentBody
import com.neqabty.meganeqabty.paymentdetails.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.payment.PaymentResponse
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentdetails.PaymentDetails
import com.neqabty.meganeqabty.paymentdetails.data.model.paymentmethods.PaymentMethodsResponse
import com.neqabty.meganeqabty.paymentdetails.data.source.PaymentDS
import com.neqabty.meganeqabty.paymentdetails.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(private val paymentDS: PaymentDS) : PaymentRepository {
    override fun getPaymentDetails(id: String, number: String): Flow<ReceiptResponse> {
        return flow {
            emit(paymentDS.getPaymentDetails(id, number))
        }
    }

    override fun getPaymentDetails(referenceCode: String): Flow<PaymentDetails> {
        return flow {
            emit((paymentDS.getPaymentDetails(referenceCode)))
        }
    }

    override fun getPaymentInfo(paymentBody: PaymentBody): Flow<PaymentResponse> {
        return flow {
            emit(paymentDS.getPaymentInfo(paymentBody))
        }
    }

    override fun getPaymentMethods(): Flow<PaymentMethodsResponse> {
        return flow {
            emit(paymentDS.getPaymentMethods())
        }
    }


}
