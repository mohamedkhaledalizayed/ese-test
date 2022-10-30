package com.neqabty.shealth.sustainablehealth.offers.domain.interactors


import com.neqabty.shealth.sustainablehealth.offers.domain.entity.offers.OffersEntity
import com.neqabty.shealth.sustainablehealth.offers.domain.repository.OffersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(private val offersRepository: OffersRepository) {
    fun build(): Flow<List<OffersEntity>> {
        return offersRepository.getOffers()
    }
}