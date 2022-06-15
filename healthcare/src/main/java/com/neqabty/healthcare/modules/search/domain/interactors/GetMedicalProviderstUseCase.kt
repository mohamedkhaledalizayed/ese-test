package com.neqabty.healthcare.modules.search.domain.interactors

import com.neqabty.healthcare.modules.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMedicalProviderstUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    fun build(): Flow<List<MedicalProviderEntity>> {
        return searchRepository.getHealthCareProviders()
    }
}