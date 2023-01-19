package com.neqabty.healthcare.sustainablehealth.search.data.repository


import com.neqabty.healthcare.sustainablehealth.search.data.model.SearchBody
import com.neqabty.healthcare.sustainablehealth.search.data.model.area.AreaModel
import com.neqabty.healthcare.sustainablehealth.search.data.model.mappers.toFiltersEntity
import com.neqabty.healthcare.sustainablehealth.search.data.model.mappers.toMedicalProviderEntity
import com.neqabty.healthcare.sustainablehealth.search.data.model.packages.DetailModel
import com.neqabty.healthcare.sustainablehealth.search.data.model.packages.PackageModel
import com.neqabty.healthcare.sustainablehealth.search.data.source.SearchDS
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.MedicalProviderEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.area.AreaListEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.filter.FiltersEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.DetailEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.packages.PackagesEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.entity.search.ProvidersEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.mappers.toProvidersEntity
import com.neqabty.healthcare.sustainablehealth.search.domain.repository.SearchRepository
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

    override fun getArea(id: Int): Flow<List<AreaListEntity>> {
        return flow {
            emit(searchDS.getAreasByGov(id).map { it.toAreaListEntity() })
        }
    }

    override fun getPackages(code: String): Flow<List<PackagesEntity>> {
        return flow {
            emit(searchDS.getPackages(code).map { it.toPackageEntity() })
        }
    }
}

private fun PackageModel.toPackageEntity(): PackagesEntity{
    return PackagesEntity(
        description = description,
        extension = extension,
        details = details.map { it.toDetailEntity() },
        followerMultiRelation = followerMultiRelation,
        hasFollower = hasFollower,
        hint = hint,
        id = id,
        maxFollower = maxFollower,
        name = name,
        price = price,
        vat = vat,
        total = total,
        recommended = recommended,
        serviceActionCode = serviceActionCode,
        serviceCode = serviceCode,
        shortDescription = shortDescription,
        insuranceAmount = insuranceAmount,
        neddedInfo = neddedInfo,
        targetGroups = targetGroups
    )
}

private fun DetailModel.toDetailEntity(): DetailEntity{
    return DetailEntity(
        cardId = cardId,
        description = description,
        id = id,
        title = title
    )
}

private fun AreaModel.toAreaListEntity(): AreaListEntity{
    return AreaListEntity(
        areaName = areaName,
        id = id
    )
}


