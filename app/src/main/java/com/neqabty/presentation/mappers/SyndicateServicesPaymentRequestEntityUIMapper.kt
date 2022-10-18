package com.neqabty.presentation.mappers

import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateServicesPaymentRequestEntity
import com.neqabty.presentation.entities.SyndicateServicesPaymentRequestUI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyndicateServicesPaymentRequestEntityUIMapper @Inject constructor() : Mapper<SyndicateServicesPaymentRequestEntity, SyndicateServicesPaymentRequestUI>() {

    override fun mapFrom(from: SyndicateServicesPaymentRequestEntity): SyndicateServicesPaymentRequestUI {
        return SyndicateServicesPaymentRequestUI(
                netAmount = from.netAmount,
                amount = from.amount,
                refId = from.refId
        )
    }
}