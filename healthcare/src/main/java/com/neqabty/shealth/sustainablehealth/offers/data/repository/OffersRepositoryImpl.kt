package com.neqabty.shealth.sustainablehealth.offers.data.repository




import com.neqabty.shealth.sustainablehealth.offers.data.source.OffersDS
import com.neqabty.shealth.sustainablehealth.offers.domain.entity.offers.OffersEntity
import com.neqabty.shealth.sustainablehealth.offers.domain.mappers.toOffersEntity
import com.neqabty.shealth.sustainablehealth.offers.domain.repository.OffersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OffersRepositoryImpl @Inject constructor(private val offersDS: OffersDS) : OffersRepository {
    override fun getOffers(): Flow<List<OffersEntity>> {
        return flow {
            emit(offersDS.getOffers().map { it.toOffersEntity() })
        }
    }
}


