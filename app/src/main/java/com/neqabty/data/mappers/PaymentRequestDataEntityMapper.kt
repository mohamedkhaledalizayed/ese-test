package com.neqabty.data.mappers

import com.neqabty.data.entities.PaymentRequestData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.PaymentRequestEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRequestDataEntityMapper @Inject constructor() : Mapper<PaymentRequestData, PaymentRequestEntity>() {

    override fun mapFrom(from: PaymentRequestData): PaymentRequestEntity {
        return PaymentRequestEntity(
                amount = from.amount,
                details = from.details?.map { PaymentRequestEntity.DetailsItem(name = it.name, totalPrice = it.totalPrice) }
        )
    }
}