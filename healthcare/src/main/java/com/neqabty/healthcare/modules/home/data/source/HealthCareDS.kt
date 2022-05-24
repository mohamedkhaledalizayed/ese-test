package com.neqabty.healthcare.modules.home.data.source

import com.neqabty.healthcare.modules.home.data.api.HealthCareApi
import com.neqabty.healthcare.modules.home.domain.entity.ProviderEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HealthCareDS @Inject constructor(private val healthCareApi: HealthCareApi) {
    fun getHealthCareProviders(): Flow<List<ProviderEntity>> {
        return flow {
            emit(healthCareApi.getHealthCareProviders())
        }
    }
}