package com.neqabty.healthcare.sustainablehealth.search.domain.interactors

import com.neqabty.healthcare.sustainablehealth.search.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.repository.SearchRepository
import com.neqabty.healthcare.sustainablehealth.search.presentation.mappers.toFiltersUi
import com.neqabty.healthcare.sustainablehealth.search.presentation.model.filters.FiltersUi
import com.neqabty.healthcare.sustainablehealth.search.presentation.model.filters.ItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMedicalProviderstUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    fun build(): Flow<List<MedicalProviderEntity>> {
        return searchRepository.getHealthCareProviders()
    }

    fun build(body: SearchBody): Flow<List<ProvidersEntity>> {
        return searchRepository.searchMedicalProviders(body)
    }

    fun getFilters(): Flow<FiltersUi> {
        return searchRepository.getFilters().map { it.toFiltersUi() }
    }

    fun getArea(id: Int): Flow<List<AreaListEntity>> {
        return searchRepository.getArea(id)
    }

    fun getPackages(code: String): Flow<List<PackagesEntity>> {
        return searchRepository.getPackages(code)
    }
}