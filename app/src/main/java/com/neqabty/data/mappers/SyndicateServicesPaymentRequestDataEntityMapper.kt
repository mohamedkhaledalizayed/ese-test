package com.neqabty.data.mappers

import com.neqabty.data.entities.SyndicateServicesPaymentRequestData
import com.neqabty.domain.common.Mapper
import com.neqabty.domain.entities.SyndicateServicesPaymentRequestEntity
import javax.inject.Inject

class SyndicateServicesPaymentRequestDataEntityMapper @Inject constructor() :
    Mapper<SyndicateServicesPaymentRequestData, SyndicateServicesPaymentRequestEntity>() {

    override fun mapFrom(from: SyndicateServicesPaymentRequestData): SyndicateServicesPaymentRequestEntity {
        return SyndicateServicesPaymentRequestEntity(
            netAmount = from.netAmount,
            amount = from.amount,
            refId = from.refId
        ) }
}