package com.neqabty.trips.modules.destinations.domain.repository

import com.neqabty.trips.modules.destinations.domain.entity.DestinationEntity
import kotlinx.coroutines.flow.Flow

interface DestinationRepository {
    fun getDestinations(): Flow<List<DestinationEntity>>
}