package com.neqabty.trips.modules.home.data.repository

import com.neqabty.trips.modules.home.data.model.mappers.toCityEntity
import com.neqabty.trips.modules.home.data.source.CitiesDS
import com.neqabty.trips.modules.home.domain.entity.CityEntity
import com.neqabty.trips.modules.home.domain.repository.CitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(private val citiesDS: CitiesDS): CitiesRepository {
    override fun getCities(): Flow<List<CityEntity>> {
        return citiesDS.getCities().map { it.map { it.toCityEntity() } }
    }
}