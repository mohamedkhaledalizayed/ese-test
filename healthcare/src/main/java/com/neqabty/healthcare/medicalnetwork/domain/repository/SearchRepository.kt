package com.neqabty.healthcare.medicalnetwork.domain.repository


import com.neqabty.healthcare.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.medicalnetwork.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.ProvidersEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getMedicalProviderDetails(id: String): Flow<ProvidersEntity>
    fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>>
    fun searchMedicalProviders(body: SearchBody): Flow<List<ProvidersEntity>>
    fun getFilters(): Flow<FiltersEntity>
    fun getArea(id: Int): Flow<List<AreaListEntity>>
}