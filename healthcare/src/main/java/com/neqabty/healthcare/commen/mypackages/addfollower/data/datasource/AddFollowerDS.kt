package com.neqabty.healthcare.commen.mypackages.addfollower.data.datasource

import com.neqabty.healthcare.commen.mypackages.addfollower.data.api.AddFollowerApi
import com.neqabty.healthcare.core.data.PreferencesHelper
import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.AddFollowerBody
import com.neqabty.healthcare.commen.mypackages.addfollower.data.model.addfollower.AddFollowerModel
import javax.inject.Inject

class AddFollowerDS @Inject constructor(private val addFollowerApi: AddFollowerApi, private val sharedPreferences: PreferencesHelper) {

    suspend fun addFollower(addFollowerBody: AddFollowerBody): AddFollowerModel {
        return addFollowerApi.addFollower(token = "Token ${sharedPreferences.token}", addFollowerBody)
    }

}