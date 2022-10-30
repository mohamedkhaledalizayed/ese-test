package com.neqabty.shealth.sustainablehealth.profile.domain.interactors



import com.neqabty.shealth.sustainablehealth.profile.data.model.UpdatePasswordBody
import com.neqabty.shealth.sustainablehealth.profile.data.model.updatepaswword.UpdatePasswordModel
import com.neqabty.shealth.sustainablehealth.profile.domain.entity.profile.ProfileEntity
import com.neqabty.shealth.sustainablehealth.profile.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class ProfileUseCase @Inject constructor(private val profileRepository: ProfileRepository) {

    fun build(token: String): Flow<ProfileEntity>{
        return profileRepository.getUserProfile(token)
    }

    fun build(body: UpdatePasswordBody): Flow<Response<UpdatePasswordModel>>{
        return profileRepository.updatePassword(body)
    }

}