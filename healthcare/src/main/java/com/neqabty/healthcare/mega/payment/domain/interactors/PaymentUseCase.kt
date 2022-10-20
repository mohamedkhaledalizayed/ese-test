package com.neqabty.healthcare.mega.payment.domain.interactors


import com.neqabty.healthcare.mega.payment.data.model.PaymentBody
import com.neqabty.healthcare.mega.payment.data.model.PaymentHomeBody
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.healthcare.mega.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.mega.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.mega.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.healthcare.mega.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.mega.payment.domain.entity.services.ServicesListEntity
import com.neqabty.healthcare.mega.payment.domain.repository.PaymentRepository
import com.neqabty.mega.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PaymentUseCase @Inject constructor(private val repository: PaymentRepository) {
    fun getServices(): Flow<List<ServicesListEntity>> {
        return repository.getServices()
    }

    fun getServiceActions(code: String): Flow<List<ServiceActionsEntity>> {
        return repository.getServiceActions(code)
    }

    fun build(id: String, code: String, number: String): Flow<Response<ReceiptResponse>> {
        return repository.getPaymentDetails(id, code, number)
    }

    fun build(paymentBody: PaymentBody): Flow<PaymentEntity> {
        return repository.payment(paymentBody)
    }

    fun build(paymentHomeBody: PaymentHomeBody): Flow<PaymentEntity> {
        return repository.paymentHome(paymentHomeBody)
    }

    fun build(): Flow<List<PaymentMethodEntity>> {
        return repository.getPaymentMethods()
    }

    fun build(referenceCode: String): Flow<PaymentStatusEntity> {
        return repository.getPaymentStatus(referenceCode)
    }

    fun getBranches(): Flow<List<BranchesEntity>> {
        return repository.getBranches()
    }
}