package com.neqabty.meganeqabty.payment.domain.interactors


import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.domain.entity.inquiryresponse.ReceiptDataEntity
import com.neqabty.meganeqabty.payment.domain.entity.payment.PaymentEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentmethods.PaymentMethodEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.meganeqabty.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.meganeqabty.payment.domain.entity.services.ServicesListEntity
import com.neqabty.meganeqabty.payment.domain.repository.PaymentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentUseCase @Inject constructor(private val repository: PaymentRepository) {
    fun getServices(code: String): Flow<List<ServicesListEntity>> {
        return repository.getServices(code)
    }

    fun getServiceActions(code: String): Flow<List<ServiceActionsEntity>> {
        return repository.getServiceActions(code)
    }

    fun build(id: String, code: String, number: String): Flow<ReceiptDataEntity> {
        return repository.getPaymentDetails(id, code, number)
    }

    fun build(paymentBody: PaymentBody): Flow<PaymentEntity> {
        return repository.payment(paymentBody)
    }

    fun build(): Flow<List<PaymentMethodEntity>> {
        return repository.getPaymentMethods()
    }

    fun build(referenceCode: String): Flow<PaymentStatusEntity> {
        return repository.getPaymentStatus(referenceCode)
    }
}