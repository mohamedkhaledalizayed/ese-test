package com.neqabty.healthcare.mega.payment.domain.repository


import com.neqabty.healthcare.mega.payment.data.model.PaymentBody
import com.neqabty.healthcare.mega.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.healthcare.mega.payment.domain.entity.branches.BranchesEntity
import com.neqabty.healthcare.mega.payment.domain.entity.payment.PaymentEntity
import com.neqabty.healthcare.mega.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.healthcare.mega.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.healthcare.mega.payment.domain.entity.services.ServicesListEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PaymentRepository {
    fun getServices(): Flow<List<ServicesListEntity>>
    fun getServiceActions(code: String): Flow<List<ServiceActionsEntity>>
    fun getPaymentDetails(id: String, code: String, number: String): Flow<Response<ReceiptResponse>>
    fun payment(paymentBody: PaymentBody): Flow<PaymentEntity>
    fun getPaymentStatus(referenceCode: String): Flow<PaymentStatusEntity>
    fun getBranches(): Flow<List<BranchesEntity>>
}