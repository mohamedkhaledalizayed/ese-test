package com.neqabty.meganeqabty.payment.domain.repository

import com.neqabty.meganeqabty.payment.data.model.PaymentBody
import com.neqabty.meganeqabty.payment.domain.entity.inquiryresponse.ReceiptDataEntity
import com.neqabty.meganeqabty.payment.domain.entity.payment.PaymentEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentmethods.PaymentMethodEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentstatus.PaymentStatusEntity
import com.neqabty.meganeqabty.payment.domain.entity.serviceactions.ServiceActionsEntity
import com.neqabty.meganeqabty.payment.domain.entity.services.ServicesListEntity
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    fun getServices(code: String): Flow<List<ServicesListEntity>>
    fun getServiceActions(code: String): Flow<List<ServiceActionsEntity>>
    fun getPaymentDetails(id: String, code: String, number: String): Flow<ReceiptDataEntity>
    fun payment(paymentBody: PaymentBody): Flow<PaymentEntity>
    fun getPaymentMethods(): Flow<List<PaymentMethodEntity>>
    fun getPaymentStatus(referenceCode: String): Flow<PaymentStatusEntity>
}