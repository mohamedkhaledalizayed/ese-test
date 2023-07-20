package com.neqabty.healthcare.commen.mypackages.packages.data.repository

import com.neqabty.healthcare.commen.mypackages.packages.data.datasource.MyPackagesDS
import com.neqabty.healthcare.commen.mypackages.packages.data.model.*
import com.neqabty.healthcare.commen.mypackages.packages.domain.entity.*
import com.neqabty.healthcare.commen.mypackages.packages.domain.repository.MyPackagesRepository
import com.neqabty.healthcare.commen.packages.packageslist.data.model.DetailModel
import com.neqabty.healthcare.commen.packages.packageslist.domain.entity.DetailEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MyPackagesRepositoryImpl @Inject constructor(private val myPackagesDS: MyPackagesDS) :
    MyPackagesRepository {

    override fun getMyPackages(phone: String): Flow<ProfileEntity> {
        return flow {
            emit(myPackagesDS.getMyPackages(phone).toProfileEntity())
        }
    }

}


private fun ProfileModel.toProfileEntity(): ProfileEntity {
    return ProfileEntity(
        data = data?.toDataEntity(),
        message = message,
        status = status
    )
}

private fun Data.toDataEntity(): DataEntity {
    return DataEntity(
        client = client.toClientEntity(),
        subscribedPackages = subscribed.map { it.toSubscribedPackageEntity() },
        wallet = wallet.toWalletEntity()
    )
}

private fun Client.toClientEntity(): ClientEntity {
    return ClientEntity(
        address = address ?: "",
        userNumber = userNumber,
        email = email ?: "",
        id = id,
        job = job ?: "",
        mobile = mobile ?: "",
        name = name ?: "",
        nationalId = nationalId ?: "",
        personalImage = personalImage ?: "",
        qrCode = qrCode ?: "",
        birthDate = birthDate ?: "",
        syndicateId = syndicateId
    )
}

private fun Subscribed.toSubscribedPackageEntity(): SubscribedPackageEntity {
    return SubscribedPackageEntity(
        packages = packages.toPackageEntity()
    )
}

private fun Package.toPackageEntity(): PackageEntity {
    return PackageEntity(
        descriptionAr = descriptionAr ?: "",
        subscriberId = subscriberId ?: "",
        followers = followers.map { it.toFollowerEntity() },
        hint = hint ?: "",
        id = id,
        details = details.map { it.toDetailEntity() },
        extension = extension ?: "",
        name = name ?: "",
        nameAr = nameAr ?: "",
        nameEn = nameEn ?: "",
        maxFollower = maxFollower,
        shortDescription = shortDescription ?: "",
        prepaid = prepaid,
        serviceCode = serviceCode ?: "",
        serviceActionCode = serviceActionCode,
        expiryDate = expiryDate,
        packagePrice = packagePrice,
        vat = vat,
        total = total,
        paid = paid,
        insuranceDocs = insuranceDocs,
        createdAt = createdAt
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

private fun Follower.toFollowerEntity(): FollowerEntity {
    return FollowerEntity(
        fullName = fullName,
        id = id,
        image = image ?: "",
        nationalId = nationalId,
        qrCode = qrCode,
        relation = relation.toRelationEntity(),
        subscriberId = subscriberId,
        relationType = relationType
    )
}

private fun Relation.toRelationEntity(): RelationEntity {
    return RelationEntity(
        createdAt = createdAt,
        id = id,
        relation = relation,
        updatedAt = updatedAt
    )
}

private fun Wallet.toWalletEntity(): WalletEntity {
    return WalletEntity(
        invitations = invitations,
        total = total
    )
}