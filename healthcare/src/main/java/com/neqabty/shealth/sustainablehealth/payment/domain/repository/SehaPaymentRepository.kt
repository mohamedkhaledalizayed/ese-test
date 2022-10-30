package com.neqabty.shealth.sustainablehealth.payment.domain.repository

import com.neqabty.shealth.sustainablehealth.payment.data.model.SehaPaymentBody
import com.neqabty.shealth.sustainablehealth.payment.domain.entity.SehaPaymentEntity
import com.neqabty.mega.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow

interface SehaPaymentRepository {

    fun payment(paymentBody: SehaPaymentBody): Flow<SehaPaymentEntity>
    fun getPaymentMethods(): Flow<List<PaymentMethodEntity>>

}