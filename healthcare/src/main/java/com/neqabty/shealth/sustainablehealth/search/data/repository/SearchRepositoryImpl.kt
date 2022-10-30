package com.neqabty.shealth.sustainablehealth.search.data.repository


import com.neqabty.shealth.sustainablehealth.search.data.model.SearchBody
import com.neqabty.shealth.sustainablehealth.search.data.model.mappers.toFiltersEntity
import com.neqabty.shealth.sustainablehealth.search.data.model.mappers.toMedicalProviderEntity
import com.neqabty.shealth.sustainablehealth.search.data.model.packages.DetailModel
import com.neqabty.shealth.sustainablehealth.search.data.model.packages.PackageModel
import com.neqabty.shealth.sustainablehealth.search.data.source.SearchDS
import com.neqabty.shealth.sustainablehealth.search.domain.entity.MedicalProviderEntity
import com.neqabty.shealth.sustainablehealth.search.domain.entity.filter.FiltersEntity
import com.neqabty.shealth.sustainablehealth.search.domain.entity.packages.DetailEntity
import com.neqabty.shealth.sustainablehealth.search.domain.entity.packages.PackagesEntity
import com.neqabty.shealth.sustainablehealth.search.domain.entity.search.ProvidersEntity
import com.neqabty.shealth.sustainablehealth.search.domain.mappers.toProvidersEntity
import com.neqabty.shealth.sustainablehealth.search.domain.repository.SearchRepository
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

    override fun getPackages(code: String): Flow<List<PackagesEntity>> {
        return flow {
            emit(searchDS.getPackages(code).map { it.toPackageEntity() })
        }
    }
}

private fun PackageModel.toPackageEntity(): PackagesEntity{
    return PackagesEntity(
        description = description,
        details = details.map { it.toDetailEntity() },
        followerMultiRelation = followerMultiRelation,
        hasFollower = hasFollower,
        hint = hint,
        id = id,
        maxFollower = maxFollower,
        name = name,
        price = price,
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


