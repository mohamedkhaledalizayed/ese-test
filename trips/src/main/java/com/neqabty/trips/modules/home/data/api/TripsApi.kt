package com.neqabty.trips.modules.home.data.api

import com.neqabty.trips.modules.home.domain.entity.TripsEntity
import kotlinx.coroutines.flow.Flow

interface TripsApi {
    suspend fun getCourse(): Flow<List<TripsEntity>>
}