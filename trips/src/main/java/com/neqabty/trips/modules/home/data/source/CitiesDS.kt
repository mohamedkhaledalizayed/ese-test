package com.neqabty.trips.modules.home.data.source

import com.neqabty.trips.modules.home.data.api.CitiesApi
import com.neqabty.trips.modules.home.data.model.CityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CitiesDS @Inject constructor(private val citiesApi: CitiesApi) {
    fun getCities(): Flow<List<CityModel>> {
        return flow {
            emit(citiesApi.getCourse().cities)
        }
    }
}