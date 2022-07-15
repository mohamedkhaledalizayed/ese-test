package com.neqabty.healthcare.modules.profile.data.repository

import com.neqabty.healthcare.modules.profile.data.model.profile.Data
import com.neqabty.healthcare.modules.profile.data.model.profile.Follower
import com.neqabty.healthcare.modules.profile.data.model.profile.ProfileModel
import com.neqabty.healthcare.modules.profile.data.source.ProfileDS
import com.neqabty.healthcare.modules.profile.domain.entity.profile.DataEntity
import com.neqabty.healthcare.modules.profile.domain.entity.profile.FollowerEntity
import com.neqabty.healthcare.modules.profile.domain.entity.profile.ProfileEntity
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
        address = address,
        email = email,
        followers = followers.map { it.toFollowerEntity() },
        id = id,
        job = job,
        mobile = mobile,
        name = name,
        nationalId = nationalId,
        personalImage = personalImage,
        qrCode = qrCode,
        syndicateId = syndicateId,
    )
}

private fun Follower.toFollowerEntity(): FollowerEntity{
    return FollowerEntity(
        fullName = fullName,
        id = id,
        image = image,
        nationalId = nationalId,
        qrCode = qrCode,
        relationType = relationType,
        subscriberId = subscriberId
    )
}