package com.neqabty.healthcare.packages.packageslist.data.repository

import com.neqabty.healthcare.packages.packageslist.data.datasource.PackagesDS
import com.neqabty.healthcare.packages.packageslist.data.model.DetailModel
import com.neqabty.healthcare.packages.packageslist.data.model.PackageModel
import com.neqabty.healthcare.packages.packageslist.data.model.insurance.InsuranceModel
import com.neqabty.healthcare.packages.packageslist.data.model.insurance.InsuranceModelList
import com.neqabty.healthcare.packages.packageslist.domain.entity.DetailEntity
import com.neqabty.healthcare.packages.packageslist.domain.entity.PackagesEntity
import com.neqabty.healthcare.packages.packageslist.domain.entity.insurance.InsuranceEntity
import com.neqabty.healthcare.packages.packageslist.domain.entity.insurance.InsuranceEntityList
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

    override fun getInsuranceDocs(packageId: String): Flow<InsuranceEntityList> {
        return flow {
            emit(packagesDS.getInsuranceDocs(packageId).toInsuranceEntityList())
        }
    }

}

private fun InsuranceModelList.toInsuranceEntityList(): InsuranceEntityList{
    return InsuranceEntityList(
        data = data.map { it.toInsuranceEntity() },
        message = message,
        status = status,
        status_code = status_code
    )
}

private fun InsuranceModel.toInsuranceEntity(): InsuranceEntity {
    return InsuranceEntity(
        created_at = created_at ?: "",
        id = id,
        name = notes ?: "",
        terms_document = terms_document ?: "",
        package_id = package_id,
        updated_at = updated_at ?: ""
    )
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