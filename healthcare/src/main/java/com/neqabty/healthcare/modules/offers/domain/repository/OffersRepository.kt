package com.neqabty.healthcare.modules.offers.domain.repository


import com.neqabty.healthcare.modules.offers.domain.entity.offers.OffersEntity
import kotlinx.coroutines.flow.Flow

interface OffersRepository {
    fun getOffers(): Flow<List<OffersEntity>>
}