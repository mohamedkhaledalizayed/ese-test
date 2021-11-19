package com.neqabty.trips.modules.home.domain.repository

import com.neqabty.trips.modules.home.domain.entity.TripsEntity
import kotlinx.coroutines.flow.Flow

interface TripsRepository {
    suspend fun getCoursesList(): Flow<List<TripsEntity>>
}