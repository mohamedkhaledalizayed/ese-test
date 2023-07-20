package com.neqabty.healthcare.medicalnetwork.data.repository


import com.neqabty.healthcare.medicalnetwork.data.model.SearchBody
import com.neqabty.healthcare.medicalnetwork.data.model.area.AreaModel
import com.neqabty.healthcare.medicalnetwork.data.model.mappers.toFiltersEntity
import com.neqabty.healthcare.medicalnetwork.data.model.mappers.toMedicalProviderEntity
import com.neqabty.healthcare.medicalnetwork.data.source.SearchDS
import com.neqabty.healthcare.medicalnetwork.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.medicalnetwork.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.medicalnetwork.domain.mappers.toProvidersEntity
import com.neqabty.healthcare.medicalnetwork.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val searchDS: SearchDS) :
    SearchRepository {
    override fun getMedicalProviderDetails(id: String): Flow<ProvidersEntity> {
        return flow {
            emit(searchDS.getMedicalProviderDetails(id).toProvidersEntity())
        }
    }

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

    override fun getArea(id: Int): Flow<List<AreaListEntity>> {
        return flow {
            emit(searchDS.getAreasByGov(id).map { it.toAreaListEntity() })
        }
    }

}

private fun AreaModel.toAreaListEntity(): AreaListEntity{
    return AreaListEntity(
        areaName = areaName,
        id = id
    )
}


