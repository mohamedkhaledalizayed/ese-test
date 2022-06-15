package com.neqabty.healthcare.modules.search.data.repository



import com.neqabty.healthcare.modules.search.data.model.mappers.toMedicalProviderEntity
import com.neqabty.healthcare.modules.search.data.source.SearchDS
import com.neqabty.healthcare.modules.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDS: SearchDS) :
    SearchRepository {
    override fun getHealthCareProviders(): Flow<List<MedicalProviderEntity>> {
        return flow {
            emit(searchDS.getHealthCareProviders().map { it.toMedicalProviderEntity() })
        }
    }
}


