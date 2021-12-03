package com.neqabty.trips.modules.destinations.domain.interactors

import com.neqabty.trips.modules.destinations.domain.entity.DestinationEntity
import com.neqabty.trips.modules.destinations.domain.repository.DestinationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDestinationsUseCase @Inject constructor(private val destinationRepository: DestinationRepository) {
    fun build(): Flow<List<DestinationEntity>> {
        return destinationRepository.getDestinations()
    }
}