package com.neqabty.trips.modules.destinations.data.repository

import com.neqabty.trips.modules.destinations.data.model.mappers.toDestinationEntity
import com.neqabty.trips.modules.destinations.data.source.DestinationDS
import com.neqabty.trips.modules.destinations.domain.entity.DestinationEntity
import com.neqabty.trips.modules.destinations.domain.repository.DestinationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DestinationRepositoryImpl @Inject constructor(private val destinationDS: DestinationDS) :
    DestinationRepository {
    override fun getDestinations(): Flow<List<DestinationEntity>> {
        return destinationDS.getDestinations().map { it.map { it.toDestinationEntity() } }
    }
}