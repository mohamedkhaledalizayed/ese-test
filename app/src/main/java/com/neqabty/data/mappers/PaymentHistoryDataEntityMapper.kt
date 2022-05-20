package com.neqabty.data.mappers

import com.neqabty.data.entities.PaymentHistoryData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.PaymentHistoryEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentHistoryDataEntityMapper @Inject constructor() : Mapper<PaymentHistoryData, PaymentHistoryEntity>() {

    override fun mapFrom(from: PaymentHistoryData): PaymentHistoryEntity {
        return PaymentHistoryEntity(
            reference = from.reference,
            code = from.code ?: "",
            amount = from.amount,
            status = from.status,
            product = from.product ?: "",
            gateway = from.gateway,
            createdAt = from.createdAt
        )
    }
}
