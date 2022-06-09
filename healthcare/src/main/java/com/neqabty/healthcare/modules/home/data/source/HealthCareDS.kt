package com.neqabty.healthcare.modules.home.data.source

import com.neqabty.healthcare.modules.home.data.api.HealthCareApi
import com.neqabty.healthcare.modules.home.data.model.MedicalProviderModel
import javax.inject.Inject

class HealthCareDS @Inject constructor(private val healthCareApi: HealthCareApi) {
    suspend fun getHealthCareProviders(): List<MedicalProviderModel> {
        return healthCareApi.getMedicalProviders().data.data
    }
}