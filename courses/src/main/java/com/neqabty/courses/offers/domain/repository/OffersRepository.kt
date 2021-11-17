package com.neqabty.courses.offers.domain.repository

import com.neqabty.courses.offers.domain.entity.OfferEntity
import kotlinx.coroutines.flow.Flow

interface OffersRepository {
    fun getOffers(): Flow<List<OfferEntity>>
}