package com.neqabty.healthcare.modules.home.domain.repository

import com.neqabty.healthcare.modules.home.domain.entity.MedicalProviderEntity
import kotlinx.coroutines.flow.Flow

interface HealthCareRepository {
    fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>>
}