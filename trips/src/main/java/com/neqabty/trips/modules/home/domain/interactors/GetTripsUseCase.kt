package com.neqabty.trips.modules.home.domain.interactors

import com.neqabty.trips.modules.home.domain.entity.TripsEntity
import com.neqabty.trips.modules.home.domain.repository.TripsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTripsUseCase @Inject constructor(private val tripsRepository: TripsRepository) {
    suspend fun build(): Flow<List<TripsEntity>> {
        return tripsRepository.getCoursesList()
    }
}