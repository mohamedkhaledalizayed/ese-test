package com.neqabty.healthcare.modules.profile.data.repository

import com.neqabty.healthcare.modules.profile.data.model.profile.*
import com.neqabty.healthcare.modules.profile.data.source.ProfileDS
import com.neqabty.healthcare.modules.profile.domain.entity.profile.*
import com.neqabty.healthcare.modules.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileDS: ProfileDS) : ProfileRepository {

    override fun getProfile(phone: String): Flow<ProfileEntity> {
        return flow {
            emit(profileDS.getProfile(phone).toProfileEntity())
        }
    }

}

private fun ProfileModel.toProfileEntity(): ProfileEntity{
    return ProfileEntity(
        data = data.toDataEntity(),
        message = message,
        status = status
    )
}

private fun Data.toDataEntity(): DataEntity{
    return DataEntity(
        client = client.toClientEntity(),
        subscribedPackages = subscribed.map { it.toSubscribedPackageEntity() },
        wallet = wallet.toWalletEntity()
    )
}

private fun Client.toClientEntity(): ClientEntity{
    return ClientEntity(
        address = address,
        email = email,
        id = id,
        job = job,
        mobile = mobile,
        name = name,
        nationalId = nationalId,
        personalImage = personalImage,
        qrCode = qrCode,
        syndicateId = syndicateId
    )
}

private fun Subscribed.toSubscribedPackageEntity(): SubscribedPackageEntity{
    return SubscribedPackageEntity(
        packages = packages.toPackageEntity()
    )
}

private fun Package.toPackageEntity(): PackageEntity{
    return PackageEntity(
        descriptionAr = descriptionAr,
        followers = followers.map { it.toFollowerEntity() },
        hint = hint,
        id = id,
        nameAr = nameAr,
        shortDescription = shortDescription,
        createdAt = createdAt
    )
}

private fun Follower.toFollowerEntity(): FollowerEntity{
    return FollowerEntity(
        fullName = fullName,
        id = id,
        image = image,
        nationalId = nationalId,
        qrCode = qrCode,
        relation = relation.toRelationEntity(),
        relationType = relationType
    )
}

private fun Relation.toRelationEntity(): RelationEntity{
    return RelationEntity(
        createdAt = createdAt,
        id = id,
        relation = relation,
        updatedAt = updatedAt
    )
}

private fun Wallet.toWalletEntity(): WalletEntity{
    return WalletEntity(
        invitations = invitations,
        total = total
    )
}