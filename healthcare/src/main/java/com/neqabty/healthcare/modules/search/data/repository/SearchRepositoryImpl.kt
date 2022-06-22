package com.neqabty.healthcare.modules.search.data.repository




import com.neqabty.healthcare.modules.search.data.model.SearchBody
import com.neqabty.healthcare.modules.search.data.model.mappers.toFiltersEntity
import com.neqabty.healthcare.modules.search.data.model.mappers.toMedicalProviderEntity
import com.neqabty.healthcare.modules.search.data.model.packages.PackageModel
import com.neqabty.healthcare.modules.search.data.source.SearchDS
import com.neqabty.healthcare.modules.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.modules.search.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.modules.search.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.modules.search.domain.mappers.toProvidersEntity
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

    override fun searchMedicalProviders(body: SearchBody): Flow<List<ProvidersEntity>> {
        return flow {
            emit(searchDS.searchMedicalProviders(body).map { it.toProvidersEntity() })
        }
    }

    override fun getFilters(): Flow<FiltersEntity> {
        return flow {
            emit(searchDS.getFilters().toFiltersEntity())
        }
    }

    override fun getPackages(): Flow<List<PackageModel>> {
        return flow {
            emit(searchDS.getPackages())
        }
    }
}


