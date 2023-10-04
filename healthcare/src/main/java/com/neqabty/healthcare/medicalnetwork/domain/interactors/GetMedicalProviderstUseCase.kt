package com.neqabty.healthcare.medicalnetwork.domain.interactors

import com.neqabty.healthcare.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.medicalnetwork.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.medicalnetwork.domain.repository.SearchRepository
import com.neqabty.healthcare.medicalnetwork.presentation.mappers.toFiltersUi
import com.neqabty.healthcare.medicalnetwork.presentation.model.filters.FiltersUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMedicalProviderstUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    fun build(id: String): Flow<ProvidersEntity> {
        return searchRepository.getMedicalProviderDetails(id)
    }

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

}