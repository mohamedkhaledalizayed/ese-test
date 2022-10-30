package com.neqabty.shealth.sustainablehealth.profile.data.repository


import com.neqabty.shealth.sustainablehealth.profile.data.model.UpdatePasswordBody
import com.neqabty.shealth.sustainablehealth.profile.data.model.profile.EntityBody
import com.neqabty.shealth.sustainablehealth.profile.data.model.profile.ProfileModel
import com.neqabty.shealth.sustainablehealth.profile.data.model.profile.UserData
import com.neqabty.shealth.sustainablehealth.profile.data.model.updatepaswword.UpdatePasswordModel
import com.neqabty.shealth.sustainablehealth.profile.data.source.ProfileDS
import com.neqabty.shealth.sustainablehealth.profile.domain.entity.profile.Data
import com.neqabty.shealth.sustainablehealth.profile.domain.entity.profile.Entity
import com.neqabty.shealth.sustainablehealth.profile.domain.entity.profile.ProfileEntity
import com.neqabty.shealth.sustainablehealth.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val profileDS: ProfileDS): ProfileRepository {

    override fun getUserProfile(token: String): Flow<ProfileEntity> {
        return flow {
            emit(profileDS.getUserProfile(token).toProfileEntity())
        }
    }

    override fun updatePassword(body: UpdatePasswordBody): Flow<Response<UpdatePasswordModel>> {
        return flow {
            emit(profileDS.updatePassword(body))
        }
    }

}



private fun ProfileModel.toProfileEntity(): ProfileEntity{
    return ProfileEntity(
        data = data.toData(),
        message = message,
        status = status
    )
}

private fun UserData.toData(): Data{
    return Data(
        email = account.email,
        entity = entity.toEntity(),
        fullName = account.fullname,
        id = id,
        image = account.image,
        mobile = account.mobile,
        membershipId = membershipId,
        nationalId = nationalId
    )
}

private fun EntityBody.toEntity(): Entity {
    return Entity(
        code = code,
        id = id,
        name = name
    )
}