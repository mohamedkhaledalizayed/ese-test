package com.neqabty.healthcare.modules.search.domain.repository


import com.neqabty.healthcare.modules.search.data.model.SearchBody
import com.neqabty.healthcare.modules.search.data.model.packages.PackageModel
import com.neqabty.healthcare.modules.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.modules.search.domain.entity.search.ProvidersEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>>
    fun searchMedicalProviders(body: SearchBody): Flow<List<ProvidersEntity>>
    fun getFilters(): Flow<FiltersEntity>
    fun getPackages(): Flow<List<PackageModel>>
}