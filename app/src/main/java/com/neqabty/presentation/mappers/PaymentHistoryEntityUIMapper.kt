package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.PaymentHistoryEntity
import com.neqabty.presentation.entities.PaymentHistoryUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentHistoryEntityUIMapper @Inject constructor() : Mapper<PaymentHistoryEntity, PaymentHistoryUI>() {

    override fun mapFrom(from: PaymentHistoryEntity): PaymentHistoryUI {
        return PaymentHistoryUI(
            reference = from.reference,
            code = from.code,
            amount = from.amount,
            status = from.status,
            product = from.product,
            gateway = from.gateway,
            createdAt = from.createdAt
        )
    }
}
