package com.neqabty.healthcare.packages.packageslist.data.repository

import com.neqabty.healthcare.packages.packageslist.data.datasource.PackagesDS
import com.neqabty.healthcare.packages.packageslist.data.model.DetailModel
import com.neqabty.healthcare.packages.packageslist.data.model.PackageModel
import com.neqabty.healthcare.packages.packageslist.domain.entity.DetailEntity
import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import com.neqabty.healthcare.packages.packageslist.domain.repository.PackagesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PackagesRepositoryImpl @Inject constructor(private val packagesDS: PackagesDS) :PackagesRepository {

    override fun getPackages(code: String): Flow<List<PackagesEntity>> {
        return flow {
            emit(packagesDS.getPackages(code).map { it.toPackageEntity() })
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