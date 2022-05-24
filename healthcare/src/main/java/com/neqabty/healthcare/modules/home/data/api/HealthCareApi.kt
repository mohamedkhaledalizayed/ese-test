package com.neqabty.healthcare.modules.home.data.api

import com.neqabty.healthcare.modules.home.domain.entity.ProviderEntity

interface HealthCareApi {
    suspend fun getHealthCareProviders(): List<ProviderEntity>
}