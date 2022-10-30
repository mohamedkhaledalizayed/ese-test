package com.neqabty.shealth.sustainablehealth.offers.domain.repository


import com.neqabty.shealth.sustainablehealth.offers.domain.entity.offers.OffersEntity
import kotlinx.coroutines.flow.Flow

interface OffersRepository {
    fun getOffers(): Flow<List<OffersEntity>>
}