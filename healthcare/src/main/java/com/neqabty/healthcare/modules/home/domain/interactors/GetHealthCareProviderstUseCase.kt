package com.neqabty.healthcare.modules.home.domain.interactors

import com.neqabty.healthcare.modules.home.domain.entity.ProviderEntity
import com.neqabty.healthcare.modules.home.domain.repository.HealthCareRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHealthCareProviderstUseCase @Inject constructor(private val healthCareRepository: HealthCareRepository) {
    fun build(): Flow<List<ProviderEntity>> {
        return healthCareRepository.getHealthCareProviders()
    }
}