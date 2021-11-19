package com.neqabty.trips.modules.home.data.repository

import com.neqabty.trips.modules.home.data.source.TripsDS
import com.neqabty.trips.modules.home.domain.entity.TripsEntity
import com.neqabty.trips.modules.home.domain.repository.TripsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripsRepositoryImpl @Inject constructor(private val tripsDS: TripsDS): TripsRepository {
    override suspend fun getCoursesList(): Flow<List<TripsEntity>> {
        return tripsDS.getCourses()
    }
}