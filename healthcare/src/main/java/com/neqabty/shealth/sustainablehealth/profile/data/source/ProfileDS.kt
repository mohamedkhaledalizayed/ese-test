package com.neqabty.shealth.sustainablehealth.profile.data.source


import com.neqabty.shealth.core.data.PreferencesHelper
import com.neqabty.shealth.sustainablehealth.profile.data.api.ProfileApi
import com.neqabty.shealth.sustainablehealth.profile.data.model.UpdatePasswordBody
import com.neqabty.shealth.sustainablehealth.profile.data.model.profile.ProfileModel
import com.neqabty.shealth.sustainablehealth.profile.data.model.updatepaswword.UpdatePasswordModel
import retrofit2.Response
import javax.inject.Inject

class ProfileDS @Inject constructor(private val profileApi: ProfileApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun getUserProfile(token: String): ProfileModel {
        return profileApi.getUserProfile(token)
    }

    suspend fun updatePassword(body: UpdatePasswordBody): Response<UpdatePasswordModel> {
        return profileApi.updatePassword("Token ${preferencesHelper.token}", body)
    }

}