package com.neqabty.trips.modules.home.data.source

import com.neqabty.trips.modules.home.data.api.TripsApi
import com.neqabty.trips.modules.home.domain.entity.TripsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TripsDS @Inject constructor(private val courseApi: TripsApi) {
    suspend fun getCourses(): Flow<List<TripsEntity>> {
        return courseApi.getCourse()
    }
}