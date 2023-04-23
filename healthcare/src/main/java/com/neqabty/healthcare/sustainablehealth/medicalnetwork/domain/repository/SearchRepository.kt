package com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.repository


import com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.medicalnetwork.domain.entity.search.ProvidersEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>>
    fun searchMedicalProviders(body: SearchBody): Flow<List<ProvidersEntity>>
    fun getFilters(): Flow<FiltersEntity>
    fun getArea(id: Int): Flow<List<AreaListEntity>>
    fun getPackages(code: String): Flow<List<PackagesEntity>>
}