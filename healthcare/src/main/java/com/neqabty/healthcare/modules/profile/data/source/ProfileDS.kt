package com.neqabty.healthcare.modules.profile.data.source

import com.neqabty.healthcare.modules.profile.data.api.ProfileApi
import com.neqabty.healthcare.modules.profile.data.model.profile.ProfileModel
import javax.inject.Inject

class ProfileDS @Inject constructor(private val profileApi: ProfileApi) {

    suspend fun getProfile(phone: String): ProfileModel{
        return profileApi.getProfile(phone)
    }

}