package com.neqabty.healthcare.chefaa.verifyuser.data.datasource

import com.neqabty.healthcare.chefaa.verifyuser.data.api.VerifyApi
import com.neqabty.healthcare.chefaa.verifyuser.data.model.VerifyUserBody
import com.neqabty.healthcare.core.data.PreferencesHelper
import javax.inject.Inject

class VerificationDS @Inject constructor(private val verifyApi: VerifyApi, private val preferencesHelper: PreferencesHelper) {

    suspend fun verifyUser(mobile: String, code: String): Boolean{
        return verifyApi.verifyUser(token = preferencesHelper.token, VerifyUserBody(mobile = mobile, verificationCode = code )).status
    }

}