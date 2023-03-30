package com.neqabty.healthcare.sustainablehealth.payment.domain.repository

import com.neqabty.healthcare.sustainablehealth.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.sustainablehealth.payment.data.model.sehapayment.SehaPaymentResponse
import com.neqabty.healthcare.sustainablehealth.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SehaPaymentRepository {

    fun payment(paymentBody: SehaPaymentBody): Flow<Response<SehaPaymentResponse>>
    fun getPaymentMethods(): Flow<List<PaymentMethodEntity>>

}