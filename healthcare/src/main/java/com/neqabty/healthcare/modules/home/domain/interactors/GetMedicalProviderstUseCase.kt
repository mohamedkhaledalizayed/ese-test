package com.neqabty.healthcare.modules.home.domain.interactors

import com.neqabty.healthcare.modules.home.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.home.domain.repository.HealthCareRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicalProviderstUseCase @Inject constructor(private val healthCareRepository: HealthCareRepository) {
    fun build(): Flow<List<MedicalProviderEntity>> {
        return healthCareRepository.getHealthCareProviders()
    }
}