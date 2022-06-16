package com.neqabty.healthcare.modules.offers.domain.mappers

import com.neqabty.healthcare.modules.offers.data.model.offers.OfferModel
import com.neqabty.healthcare.modules.offers.domain.entity.offers.OffersEntity

fun OfferModel.toOffersEntity(): OffersEntity{
    return OffersEntity(
        title = title,
        description = description,
        expiryDate = expiryDate,
        percentage = percentage
    )
}