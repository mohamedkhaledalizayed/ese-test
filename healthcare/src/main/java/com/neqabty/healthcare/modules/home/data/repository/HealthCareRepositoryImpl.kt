package com.neqabty.healthcare.modules.home.data.repository

import com.neqabty.healthcare.modules.home.data.model.MedicalProviderModel
import com.neqabty.healthcare.modules.home.data.model.mappers.toMedicalProviderEntity
import com.neqabty.healthcare.modules.home.data.source.HealthCareDS
import com.neqabty.healthcare.modules.home.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.home.domain.repository.HealthCareRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HealthCareRepositoryImpl @Inject constructor(private val healthCareDS: HealthCareDS) :
    HealthCareRepository {
    override fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>> {
        return flow {
            emit(healthCareDS.getHealthCareProviders().map { it.toMedicalProviderEntity() })
        }
    }
}


