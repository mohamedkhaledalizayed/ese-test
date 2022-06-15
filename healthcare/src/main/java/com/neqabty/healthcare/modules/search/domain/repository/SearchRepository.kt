package com.neqabty.healthcare.modules.search.domain.repository

import com.neqabty.healthcare.modules.search.domain.entity.MedicalProviderEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>>
}