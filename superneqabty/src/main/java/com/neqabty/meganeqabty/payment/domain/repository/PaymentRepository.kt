package com.neqabty.meganeqabty.payment.domain.repository

import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.data.model.PaymentHomeBody
import com.neqabty.meganeqabty.payment.data.model.inquiryresponse.ReceiptResponse
import com.neqabty.meganeqabty.payment.domain.entity.branches.BranchesEntity
import com.neqabty.meganeqabty.payment.domain.entity.inquiryresponse.ReceiptDataEntity
import com.neqabty.meganeqabty.payment.domain.entity.payment.PaymentEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentmethods.PaymentMethodEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.meganeqabty.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.meganeqabty.payment.domain.entity.services.ServicesListEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PaymentRepository {
    fun getServices(): Flow<List<ServicesListEntity>>
    fun getServiceActions(code: String): Flow<List<ServiceActionsEntity>>
    fun getPaymentDetails(id: String, code: String, number: String): Flow<Response<ReceiptResponse>>
    fun payment(paymentBody: PaymentBody): Flow<PaymentEntity>
    fun paymentHome(paymentHomeBody: PaymentHomeBody): Flow<PaymentEntity>
    fun getPaymentMethods(): Flow<List<PaymentMethodEntity>>
    fun getPaymentStatus(referenceCode: String): Flow<PaymentStatusEntity>
    fun getBranches(): Flow<List<BranchesEntity>>
}