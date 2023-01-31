package com.neqabty.healthcare.sustainablehealth.home.data.repository


import com.neqabty.healthcare.sustainablehealth.home.data.source.HomeDS
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.AboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.mappers.toAboutEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.repository.HomeRepository
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.packages.DetailModel
import com.neqabty.healthcare.sustainablehealth.home.data.model.about.packages.PackageModel
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages.DetailEntity
import com.neqabty.healthcare.sustainablehealth.home.domain.entity.about.packages.PackagesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val homeDS: HomeDS) : HomeRepository {
    override fun getAboutList(): Flow<List<AboutEntity>> {
        return flow {
            emit(homeDS.getAboutList().map { it.toAboutEntity() })
        }
    }

    override fun getPackages(code: String): Flow<List<PackagesEntity>> {
        return flow {
            emit(homeDS.getPackages(code).map { it.toPackageEntity() })
        }
    }
}

private fun PackageModel.toPackageEntity(): PackagesEntity {
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

private fun DetailModel.toDetailEntity(): DetailEntity {
    return DetailEntity(
        cardId = cardId,
        description = description,
        id = id,
        title = title
    )
}


