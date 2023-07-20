package com.neqabty.healthcare.commen.packages.payment.domain.repository

import com.neqabty.healthcare.commen.packages.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.commen.packages.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.commen.packages.payment.domain.entity.paymentmethods.PaymentEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PaymentRepository {

    fun payment(paymentBody: SehaPaymentBody): Flow<Response<SehaPaymentResponse>>
    fun getPaymentMethods(code: String): Flow<PaymentEntity>

}