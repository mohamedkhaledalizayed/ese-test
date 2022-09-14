package com.neqabty.healthcare.modules.payment.domain.repository

import com.neqabty.healthcare.modules.payment.data.model.SehaPaymentBody
import com.neqabty.healthcare.modules.payment.domain.entity.SehaPaymentEntity
import com.neqabty.meganeqabty.payment.domain.entity.payment.PaymentEntity
import com.neqabty.meganeqabty.payment.domain.entity.paymentmethods.PaymentMethodEntity
import kotlinx.coroutines.flow.Flow

interface SehaPaymentRepository {

    fun payment(paymentBody: SehaPaymentBody): Flow<SehaPaymentEntity>
    fun getPaymentMethods(): Flow<List<PaymentMethodEntity>>

}