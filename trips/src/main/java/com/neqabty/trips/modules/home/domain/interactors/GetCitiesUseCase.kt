package com.neqabty.trips.modules.home.domain.interactors

import com.neqabty.trips.modules.home.domain.entity.CityEntity
import com.neqabty.trips.modules.home.domain.repository.CitiesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCitiesUseCase @Inject constructor(private val citiesRepository: CitiesRepository) {
    fun build(): Flow<List<CityEntity>> {
        return citiesRepository.getCities()
    }
}