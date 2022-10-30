package com.neqabty.shealth.sustainablehealth.offers.domain.mappers

import com.neqabty.shealth.sustainablehealth.offers.data.model.offers.OfferModel
import com.neqabty.shealth.sustainablehealth.offers.domain.entity.offers.OffersEntity

fun OfferModel.toOffersEntity(): OffersEntity{
    return OffersEntity(
        title = title,
        description = description,
        expiryDate = expiryDate,
        percentage = percentage
    )
}