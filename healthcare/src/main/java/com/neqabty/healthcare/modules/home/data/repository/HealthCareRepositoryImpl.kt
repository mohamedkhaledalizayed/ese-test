package com.neqabty.healthcare.modules.home.data.repository

import com.neqabty.healthcare.modules.home.data.source.HealthCareDS
import com.neqabty.healthcare.modules.home.domain.entity.ProviderEntity
import com.neqabty.healthcare.modules.home.domain.repository.HealthCareRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HealthCareRepositoryImpl @Inject constructor(private val healthCareDS: HealthCareDS):
    HealthCareRepository {
    override fun getHealthCareProviders(): Flow<List<ProviderEntity>> {
        return healthCareDS.getHealthCareProviders()
    }
}