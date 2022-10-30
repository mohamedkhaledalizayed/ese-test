package com.neqabty.shealth.sustainablehealth.profile.domain.repository


import com.neqabty.shealth.sustainablehealth.profile.data.model.UpdatePasswordBody
import com.neqabty.shealth.sustainablehealth.profile.data.model.updatepaswword.UpdatePasswordModel
import com.neqabty.shealth.sustainablehealth.profile.domain.entity.profile.ProfileEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface ProfileRepository {
    fun getUserProfile(token: String): Flow<ProfileEntity>
    fun updatePassword(body: UpdatePasswordBody): Flow<Response<UpdatePasswordModel>>
}