package com.neqabty.courses.offers.data.repository

import com.neqabty.courses.offers.data.model.mapper.toOfferEntity
import com.neqabty.courses.offers.data.source.OffersDS
import com.neqabty.courses.offers.domain.entity.OfferEntity
import com.neqabty.courses.offers.domain.repository.OffersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(private val offersDS: OffersDS): OffersRepository {
    override fun getOffers(): Flow<List<OfferEntity>> {
        return offersDS.getOffers().map { it.map { it.toOfferEntity() } }
    }
}