package com.neqabty.trips.modules.home.domain.repository

import com.neqabty.trips.modules.home.domain.entity.CityEntity
import kotlinx.coroutines.flow.Flow

interface CitiesRepository {
    fun getCities(): Flow<List<CityEntity>>
}