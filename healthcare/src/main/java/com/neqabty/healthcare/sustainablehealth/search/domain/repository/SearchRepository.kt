package com.neqabty.healthcare.sustainablehealth.search.domain.repository


import com.neqabty.healthcare.sustainablehealth.search.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ProvidersEntity
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>>
    fun searchMedicalProviders(body: SearchBody): Flow<List<ProvidersEntity>>
    fun getFilters(): Flow<FiltersEntity>
    fun getArea(id: Int): Flow<List<AreaListEntity>>
}