package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.PaymentRequestEntity
import com.neqabty.presentation.entities.PaymentRequestUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentRequestEntityUIMapper @Inject constructor() : Mapper<PaymentRequestEntity, PaymentRequestUI>() {

    override fun mapFrom(from: PaymentRequestEntity): PaymentRequestUI {
        return PaymentRequestUI(
                amount = from.amount,
                details = from.details?.map { PaymentRequestUI.DetailsItem(name = it.name, totalPrice = it.totalPrice) }
        )
    }
}